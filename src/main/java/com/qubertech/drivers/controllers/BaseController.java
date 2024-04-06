package com.qubertech.drivers.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"", "/"})
public class BaseController
{
    @GetMapping(path = {"", "/"})
    public ResponseEntity<Object> home() {
        String responseJson = "{\"status\": \"ok\" }";
        return ResponseEntity.ok().body(responseJson);
    }
}
