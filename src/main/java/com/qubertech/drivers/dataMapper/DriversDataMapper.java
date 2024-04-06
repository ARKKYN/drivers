package com.qubertech.drivers.dataMapper;

import com.qubertech.drivers.api.ExcelAPI;
import com.qubertech.drivers.entities.Driver;
import com.qubertech.drivers.exception.ExcelTableLoadFailure;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriversDataMapper implements IDataMapper<Driver>
{
    
    @Autowired
    private ExcelAPI api;
    
    private static Logger logger = LogManager.getLogger(DriversDataMapper.class);
    
    
    private Driver mapRow(Row row)
    {
        Driver driver = new Driver();
        driver.setId((long) row.getCell(0).getNumericCellValue());
        driver.setEmployeeId((long) row.getCell(1).getNumericCellValue());
        driver.setDistanceKm((row.getCell(2).getNumericCellValue()));
        driver.setDrivingTime(String.valueOf(row.getCell(3).getStringCellValue()));
        driver.setHighestSpeedKmh(Double.parseDouble(String.valueOf(row.getCell(4).getNumericCellValue())));
        return driver;
    }
    
    private List<Driver> buildListWithMappedRow(Sheet sheet)
    {
        List<Driver> drivers = new ArrayList<Driver>();
        for (Row row : sheet)
        {
            if (row.getRowNum() <= 2)
            {
                continue; // Skip headers
            }
            if (row.getCell(0) == null) {
                break;
            }
            Driver driver = this.mapRow(row);
            drivers.add(driver);
        }
        
        return drivers;
    }
    
    @Override
    public List<Driver> mapData() throws ExcelTableLoadFailure
    {
        Workbook excelTable = this.getExcelTableFromWorkSheet();
        Sheet sheet = excelTable.getSheetAt(0);
        List<Driver> drivers = this.buildListWithMappedRow(sheet);
        return drivers;
    }
    
    private Workbook getExcelTableFromWorkSheet() throws ExcelTableLoadFailure
    {
        try
        {
            Workbook workbook = api.workbook();
            return workbook;
        } catch (IOException e)
        {
            throw new ExcelTableLoadFailure("Loading Table Failure Exception");
        }
    }
}
