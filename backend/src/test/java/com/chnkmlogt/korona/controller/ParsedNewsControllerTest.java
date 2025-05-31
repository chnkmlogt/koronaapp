package com.chnkmlogt.korona.controller;

import com.chnkmlogt.korona.model.ParsedNews;
import com.chnkmlogt.korona.service.ParsedNewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ParsedNewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParsedNewsService newsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveNews_shouldReturnSavedNews_whenRawTextIsValid() throws Exception {
        ParsedNews mockNews = new ParsedNews();
        mockNews.setId("1");
        mockNews.setCity("SampleCity");
        mockNews.setDate(java.time.LocalDate.now());
        mockNews.setCases(10);
        mockNews.setDeaths(2);
        mockNews.setRecovered(5);
        mockNews.setRawText("Example news content");

        Mockito.when(newsService.parseAndSave(anyString())).thenReturn(mockNews);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Example raw news content"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.city").value("SampleCity"))
                .andExpect(jsonPath("$.cases").value(10))
                .andExpect(jsonPath("$.deaths").value(2))
                .andExpect(jsonPath("$.recovered").value(5))
                .andExpect(jsonPath("$.rawText").value("Example news content"));
    }
}
