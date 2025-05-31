
package com.chnkmlogt.korona.dto;

import java.time.LocalDate;

public class DailyReportDTO {
    private LocalDate date;
    private int totalCases;
    private int totalDeaths;
    private int totalRecovered;

    public DailyReportDTO(LocalDate date, int totalCases, int totalDeaths, int totalRecovered) {
        this.date = date;
        this.totalCases = totalCases;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
    }

    public LocalDate getDate() { return date; }
    public int getTotalCases() { return totalCases; }
    public void setTotalCases(int totalCases) { this.totalCases = totalCases; }
    public int getTotalDeaths() { return totalDeaths; }
    public void setTotalDeaths(int totalDeaths) { this.totalDeaths = totalDeaths; }
    public int getTotalRecovered() { return totalRecovered; }
    public void setTotalRecovered(int totalRecovered) { this.totalRecovered = totalRecovered; }
}
