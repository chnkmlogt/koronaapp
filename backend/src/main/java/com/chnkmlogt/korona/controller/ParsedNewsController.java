
package com.chnkmlogt.korona.controller;

import com.chnkmlogt.korona.dto.DailyReportDTO;
import com.chnkmlogt.korona.model.ParsedNews;
import com.chnkmlogt.korona.repository.ParsedNewsRepository;
import com.chnkmlogt.korona.service.ParsedNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ParsedNewsController {
    private final ParsedNewsService newsService;

    @Autowired
    private ParsedNewsRepository parsedNewsRepository;

    public ParsedNewsController(ParsedNewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/news")
    public ResponseEntity<ParsedNews> saveNews(@RequestBody String rawText) {
        return ResponseEntity.ok(newsService.parseAndSave(rawText));
    }

    @GetMapping("/news")
    public Page<ParsedNews> getNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("date").descending());
        return parsedNewsRepository.findAll(pageable);
    }
    @GetMapping("/reports")
    public ResponseEntity<List<DailyReportDTO>> getReport(
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "false") boolean cumulative) {
        return ResponseEntity.ok(newsService.getDailyReport(city, cumulative));
    }
}
