package com.chnkmlogt.korona.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(CityService.class)
public class CityServiceTest {

    @Mock
    private CityService cityService;


    @Test
    public void testGetAllCities() throws Exception {
        ClassPathResource resource = new ClassPathResource("cities.txt");
        String sampleCities = "New York\nLos Angeles\nChicago\nHouston";
        when(cityService.getAllCities()).thenReturn(Arrays.asList(sampleCities.split("\n")));

        List<String> cities = cityService.getAllCities();
        assertEquals(4, cities.size());
        assertEquals("New York", cities.get(0));
        assertEquals("Los Angeles", cities.get(1));
    }

    @Test
    public void testGetAllCitiesWhenFileIsEmpty() throws Exception {
        when(cityService.getAllCities()).thenReturn(Collections.emptyList());

        List<String> cities = cityService.getAllCities();
        assertEquals(0, cities.size());
    }
}
