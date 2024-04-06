package com.qubertech.drivers.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qubertech.drivers.entities.Driver;
import com.qubertech.drivers.exception.ValidationException;
import com.qubertech.drivers.services.DriverService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriversController {
    
    @Autowired
    DriverService service;
    
    private static Logger logger = LogManager.getLogger(DriversController.class);
    
    private String buildJson(List l) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = objectMapper.writeValueAsString(l);
        return jsonArray;
    }
    
    @GetMapping(path = {"", "/"}, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllDrivers(
        @RequestParam(required = false, defaultValue = "id") String sortBy,
        @RequestParam(required = false, defaultValue = "asc") String direction
    ) throws Exception {
        List<Driver> response = service.getAllDrivers(sortBy, direction);
        return ResponseEntity.ok(buildJson(response));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Object> searchEmployees(@RequestParam String employeeId) throws Exception  {
        List<Driver> response =  service.searchDrivers(employeeId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/topKmDriven")
    public ResponseEntity<Object> getTopDriversByKilometersDrivenInShortestTime(@RequestParam(defaultValue = "10") int limit) throws Exception  {
        List<Driver> response =  service.getTopDriversByKilometersDrivenInShortestTime(limit);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/topSafeSpeed")
    public ResponseEntity<Object> getTopSafeSpeedDrivers(@RequestParam(defaultValue = "10") int limit) throws Exception  {
        List<Driver> response = service.getTopSafeSpeedDrivers(limit);
        return ResponseEntity.ok(response);
    }
    
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleError(Exception e, WebRequest r) {
   
        String responseJson = "{\"message\":" + e.getMessage() + "}";
        if (e instanceof ValidationException) {
            return ResponseEntity.unprocessableEntity().body(responseJson);
        }
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(responseJson);
    }
    
}