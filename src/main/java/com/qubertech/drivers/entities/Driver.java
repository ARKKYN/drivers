package com.qubertech.drivers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

@Component
public class Driver
{
    
    private Long id;
    
    private Long employeeId;
    
    private double distanceKm;
    
    private String drivingTime;
    
    private double highestSpeedKmh;
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Long getEmployeeId()
    {
        return employeeId;
    }
    
    public void setEmployeeId(Long employeeId)
    {
        this.employeeId = employeeId;
    }
    
    public double getDistanceKm()
    {
        return distanceKm;
    }
    
    public void setDistanceKm(double distanceKm)
    {
        this.distanceKm = distanceKm;
    }
    
    public String getDrivingTime()
    {
        return drivingTime;
    }
    
    public void setDrivingTime(String drivingTime)
    {
        this.drivingTime = drivingTime;
    }
    
    public double getHighestSpeedKmh()
    {
        return highestSpeedKmh;
    }
    
    public void setHighestSpeedKmh(double highestSpeedKmh)
    {
        this.highestSpeedKmh = highestSpeedKmh;
    }
    
    @JsonIgnore
    public double getKmPerMinute() {
        String[] parts = drivingTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        double totalMinutes = hours * 60 + minutes + (double) seconds / 60;
        return distanceKm / totalMinutes;
    }
    

    
}

