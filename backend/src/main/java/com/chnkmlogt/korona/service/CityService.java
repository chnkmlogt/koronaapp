package com.chnkmlogt.korona.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

@Service
public class CityService {
    public List<String> getAllCities() {
        try {
            ClassPathResource resource = new ClassPathResource("cities.txt");
            return Files.readAllLines(resource.getFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
