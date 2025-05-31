package com.chnkmlogt.korona.util;

import com.chnkmlogt.korona.model.ParsedNews;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsParser {

    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{2})\\.(\\d{2})\\.(\\d{4})");
    private static final Pattern CASES_PATTERN = Pattern.compile("(\\d+)[^\\d]*(?:yeni\\s*)?\\bvaka\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern DEATHS_PATTERN = Pattern.compile("(\\d+)[^\\d]*(?:kişi\\s*)?(?:de\\s*)?(?:vefat etti|ölü|öldü)", Pattern.CASE_INSENSITIVE);
    private static final Pattern RECOVERED_PATTERN = Pattern.compile("(\\d+)[^\\d]*(?:kişi\\s*)?(?:de\\s*)?(?:iyileşti|taburcu oldu|taburcu)", Pattern.CASE_INSENSITIVE);

    public static ParsedNews parse(String text) {
        ParsedNews result = new ParsedNews();

        Integer cases = extractInt(CASES_PATTERN, text);
        Integer deaths = extractInt(DEATHS_PATTERN, text);
        Integer recovered = extractInt(RECOVERED_PATTERN, text);
        String city = extract(CityPatternLoader.getCityPattern(), text);
        LocalDate date = extractLocalDate(DATE_PATTERN, text);

        result.setCity(city);
        result.setDate(date);
        result.setCases(cases);
        result.setDeaths(deaths != null ? deaths : 0);
        result.setRecovered(recovered != null ? recovered : 0);
        result.setRawText(text);

        return result;
    }

    private static String extract(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : "";
    }

    private static Integer extractInt(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    private static LocalDate extractLocalDate(Pattern pattern, String text) {
        Matcher dateMatcher = pattern.matcher(text);
        if (dateMatcher.find()) {
            int day = Integer.parseInt(dateMatcher.group(1));
            int month = Integer.parseInt(dateMatcher.group(2));
            int year = Integer.parseInt(dateMatcher.group(3));
            return LocalDate.of(year, month, day);
        }
        return null;
    }

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
