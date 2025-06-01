package com.chnkmlogt.korona.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
    public List<String> getAllCities() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("cities.txt").getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
