package com.chnkmlogt.korona.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CityPatternLoader {

    private static final Pattern cityPattern;

    static {
        try (InputStream is = CityPatternLoader.class.getResourceAsStream("/cities.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            List<String> cities = reader.lines()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            String regex = "\\b(" + String.join("|", cities) + ")\\b";
            cityPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        } catch (IOException e) {
            throw new RuntimeException("Şehir dosyası yüklenemedi", e);
        }
    }

    public static Pattern getCityPattern() {
        return cityPattern;
    }
}
