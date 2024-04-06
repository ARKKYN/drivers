package com.qubertech.drivers.api;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ExcelAPI {
    
    @Value("${datasource.path}")
    String path;
    
    @Bean
    public Workbook workbook() throws java.io.IOException
    {
        java.io.FileInputStream file = new java.io.FileInputStream(path);
        return WorkbookFactory.create(file);
    }
}