
package com.chnkmlogt.korona.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "parsed_news")
public class ParsedNews {
    @Id
    private String id;
    private String city;
    private LocalDate date;
    private int cases;
    private int deaths;
    private int recovered;
    private String rawText;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getCases() { return cases; }
    public void setCases(int cases) { this.cases = cases; }

    public int getDeaths() { return deaths; }
    public void setDeaths(int deaths) { this.deaths = deaths; }

    public int getRecovered() { return recovered; }
    public void setRecovered(int recovered) { this.recovered = recovered; }

    public String getRawText() { return rawText; }
    public void setRawText(String rawText) { this.rawText = rawText; }
}
