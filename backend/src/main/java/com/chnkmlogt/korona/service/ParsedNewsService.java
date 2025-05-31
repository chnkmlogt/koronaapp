package com.chnkmlogt.korona.service;

import com.chnkmlogt.korona.dto.DailyReportDTO;
import com.chnkmlogt.korona.model.ParsedNews;
import com.chnkmlogt.korona.repository.ParsedNewsRepository;
import com.chnkmlogt.korona.util.NewsParser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ParsedNewsService {
    private final ParsedNewsRepository repository;

    public ParsedNewsService(ParsedNewsRepository repository) {
        this.repository = repository;
    }

    public ParsedNews parseAndSave(String rawText) {
        ParsedNews parsed = NewsParser.parse(rawText);
        return repository.save(parsed);
    }

    public List<ParsedNews> getAllNews() {
        return repository.findAll();
    }

    public List<DailyReportDTO> getDailyReport(String city, boolean cumulative) {
        List<ParsedNews> data = city != null && !city.isBlank()
                ? repository.findAll().stream()
                .filter(p -> city.equalsIgnoreCase(p.getCity()))
                .collect(Collectors.toList())
                : repository.findAll();

        Map<LocalDate, DailyReportDTO> grouped = new TreeMap<>();
        for (ParsedNews item : data) {
            grouped.putIfAbsent(item.getDate(), new DailyReportDTO(item.getDate(), 0, 0, 0));
            DailyReportDTO report = grouped.get(item.getDate());
            report.setTotalCases(report.getTotalCases() + item.getCases());
            report.setTotalDeaths(report.getTotalDeaths() + item.getDeaths());
            report.setTotalRecovered(report.getTotalRecovered() + item.getRecovered());
        }

        List<DailyReportDTO> reportList = new ArrayList<>(grouped.values());

        if (cumulative) {
            int sumCases = 0, sumDeaths = 0, sumRecovered = 0;
            for (DailyReportDTO r : reportList) {
                sumCases += r.getTotalCases();
                sumDeaths += r.getTotalDeaths();
                sumRecovered += r.getTotalRecovered();
                r.setTotalCases(sumCases);
                r.setTotalDeaths(sumDeaths);
                r.setTotalRecovered(sumRecovered);
            }
        }

        return reportList;
    }
}
