package com.qubertech.drivers.dataTables;


import com.qubertech.drivers.data.DataEngine;
import com.qubertech.drivers.dataMapper.DriversDataMapper;
import com.qubertech.drivers.entities.Driver;
import com.qubertech.drivers.exception.ExcelTableLoadFailure;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DriversTable implements ITables<Driver>
{
    private static Logger logger = LogManager.getLogger(DriversTable.class);
    
    @Autowired
    private DriversDataMapper mapper;
    
    
    @Autowired
    private DataEngine<Driver> engine;
    
    List<Driver> table;
    
   
    
    public List<Driver> getTableData()
    {
        return this.table;
    }
    
    public void setTableData(List<Driver> drivers)
    {
        this.table = drivers;
    }
    
  
    
    public void loadTable() throws ExcelTableLoadFailure
    {
        
        List<Driver> data = this.mapper.mapData();
    
        this.setTableData(data);
        this.engine.set(this.table);
    }
    
    public DataEngine<Driver> getQuery() throws ExcelTableLoadFailure
    {
        this.loadTable();
        return engine;
    }
}
