package com.qubertech.drivers.data;

import com.qubertech.drivers.controllers.DriversController;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DataEngine<T> implements IExcelEngine<DataEngine, T>
{
    
    private static Logger logger = LogManager.getLogger(DriversController.class);
    
    private List<T> table;
    
    public void set(List<T> data)
    {
        this.table = data;
    }
    
    @Override
    public List<T> findAll()
    {
        return this.table;
    }
    
    public DataEngine<T> sortBy(String field, EOrderBy order)
    {
        this.table.sort((currentObject, nextObject) ->
        {
            Comparable left = getFieldComparable(currentObject, field);
            Comparable right = getFieldComparable(nextObject, field);
            return order == EOrderBy.ASC ? left.compareTo(right) : right.compareTo(left);
        });
        return this;
    }
    
    public DataEngine<T> betweenRange(String field, int minValue, int maxValue)
    {
        this.table = this.table.stream()
                               .filter(o ->
                               {
                                   Comparable value = getFieldComparable(o, field);
                                   if (value instanceof Integer)
                                   {
                                       return ((Integer) value >= minValue) && ((Integer) value <= maxValue);
                                   } else if (value instanceof Double)
                                   {
                                       return ((Double) value >= minValue) && ((Double) value <= maxValue);
                                   }
                                   return false;
                               })
                               .collect(Collectors.toList());
        return this;
    }
    
    
    public DataEngine<T> filter(String field, EComparisonOperator operator, String value)
    {
        this.table = this.table.stream()
                               .filter(o -> operator != EComparisonOperator.LIKE ? compareFieldWithOperator(o, field,
                                   operator, value) : search(o, value))
                               .collect(Collectors.toList());
        return this;
    }
    
    
    private Comparable getFieldComparable(Object object, String field)
    {
        try
        {
            Field declaredField = object.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);  // since it is a private field
            return (Comparable) declaredField.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException("Field '" + field + "' not found or inaccessible");
        }
    }
    
    private boolean compareFieldWithOperator(Object object, String field, EComparisonOperator operator, String value)
    {
        try
        {
            Comparable fieldValue = getFieldComparable(object, field);
            switch (operator)
            {
                case LESS_THAN:
                    return fieldValue.compareTo(value) < 0;
                case LESS_THAN_OR_EQUAL:
                    return fieldValue.compareTo(value) <= 0;
                case EQUAL:
                    return fieldValue.compareTo(value) == 0;
                case GREATER_THAN_OR_EQUAL:
                    return fieldValue.compareTo(value) >= 0;
                case GREATER_THAN:
                    return fieldValue.compareTo(value) > 0;
                default:
                    return false;
            }
        } catch (IllegalArgumentException e)
        {
            logger.error(e.getStackTrace());
            return false;
        }
        
    }
    
    private boolean search(Object o, String searchString)
    {
        String[] searchWords = searchString.split(" ");
        for (String searchWord : searchWords)
        {
            boolean found = searchInObject(o, searchWord);
            if (found)
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean searchInObject(Object o, String searchWord)
    {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            try
            {
                Object fieldValue = field.get(o);
                if (fieldValue != null && fieldValue.toString().toLowerCase().contains(searchWord.toLowerCase()))
                {
                    return true;
                }
            } catch (IllegalAccessException e)
            {
                logger.error(e.getStackTrace());
                return false;
            }
        }
        return false;
    }
    
    public DataEngine<T> limit(int by)
    {
        this.table = this.table.stream().limit(by).collect(Collectors.toList());
        return this;
    }
    
}
