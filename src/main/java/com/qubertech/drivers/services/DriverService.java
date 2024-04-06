package com.qubertech.drivers.services;


import com.qubertech.drivers.entities.Driver;
import com.qubertech.drivers.exception.ExcelTableLoadFailure;
import com.qubertech.drivers.repository.DriverRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    
    @Autowired
    DriverRepository repository;
    
   
    public List<com.qubertech.drivers.entities.Driver> getAllDrivers(String sortBy,
        String direction) throws ExcelTableLoadFailure
    {
  
        return repository.find(sortBy, direction);
    }
    
    public List<Driver> searchDrivers(String employeeIds) throws ExcelTableLoadFailure
    {
        return repository.search("employeeId", employeeIds);
    }
    
    public List<Driver> getTopDriversByKilometersDrivenInShortestTime(int limit) throws ExcelTableLoadFailure
    {
        return repository.findDriversWithMoreTravelDistance(limit);
    }
    
    public List<Driver> getTopSafeSpeedDrivers(int limit) throws ExcelTableLoadFailure
    {
        return repository.findDriversWithSafeSpeed(limit);
    }
}
