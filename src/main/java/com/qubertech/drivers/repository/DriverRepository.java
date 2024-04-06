package com.qubertech.drivers.repository;

import com.qubertech.drivers.data.EComparisonOperator;
import com.qubertech.drivers.data.EOrderBy;
import com.qubertech.drivers.dataTables.DriversTable;
import com.qubertech.drivers.entities.Driver;
import com.qubertech.drivers.exception.ExcelTableLoadFailure;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable
public class DriverRepository {
    
    @Autowired
    private DriversTable table;
    
    @Cacheable("drivers")
    public List<Driver> find() throws ExcelTableLoadFailure
    {
        return table.getQuery().findAll();
    }
    
    @Cacheable("drivers")
    public List<Driver> find(String sortField, String sortOrder) throws ExcelTableLoadFailure
    {
        return table.getQuery().sortBy(sortField, sortOrder == "asc" ? EOrderBy.ASC : EOrderBy.DESC).findAll();
    }
    
    @Cacheable("drivers")
    public List<Driver> search(String field, String searchFor) throws ExcelTableLoadFailure
    {
        return table.getQuery().filter(field, EComparisonOperator.LIKE, searchFor).findAll();
    }
    
    @Cacheable("drivers")
    public List<Driver> findDriversWithSafeSpeed(int limit) throws ExcelTableLoadFailure {
        return table.getQuery().betweenRange("highestSpeedKmh",90, 100).sortBy("highestSpeedKmh", EOrderBy.ASC).limit(limit).findAll();
    }
    
    @Cacheable("drivers")
    public List<Driver> findDriversWithMoreTravelDistance(int limit) throws ExcelTableLoadFailure {
         List<Driver> resp =  table.getQuery().findAll();
         resp.sort(Comparator.comparingDouble(Driver::getKmPerMinute).reversed());
         resp = resp.stream().limit(limit).collect(Collectors.toList());
         return resp;
    }

}