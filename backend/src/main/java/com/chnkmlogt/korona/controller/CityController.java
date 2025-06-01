package com.chnkmlogt.korona.controller;

import com.chnkmlogt.korona.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/cities")
    public List<String> getCities() {
        return cityService.getAllCities();
    }
}
