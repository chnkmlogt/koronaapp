package com.chnkmlogt.korona.service;

import com.chnkmlogt.korona.repository.ParsedNewsRepository;
import com.chnkmlogt.korona.util.NewsParser;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParsedNewsServiceTest {


    @Mock
    private NewsParser newsParser;

    @Mock
    private ParsedNewsRepository parsedNewsRepository;

    @InjectMocks
    private ParsedNewsService parsedNewsService;
    /*

     */
/**
 * Test that the parseAndSave method correctly parses and saves a valid raw text.
 *//*

    @Test
    void testParseAndSave_ValidRawText() {
        // Arrange
        String rawText = " 20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi korona dan vefat etti. 5 kişide taburcu oldu.";
        ParsedNews parsedNews = new ParsedNews();
        parsedNews.setId("1");
        parsedNews.setCity("Ankara");
        parsedNews.setDate(LocalDate.of(2020, 04, 20));
        parsedNews.setCases(15);
        parsedNews.setDeaths(1);
        parsedNews.setRecovered(5);
        parsedNews.setRawText(rawText);

        when(newsParser.parse(rawText)).thenReturn(parsedNews);
        when(parsedNewsRepository.save(parsedNews)).thenReturn(parsedNews);

        // Act
        ParsedNews savedNews = parsedNewsService.parseAndSave(rawText);

        // Assert
        assertEquals(parsedNews.getCity(), savedNews.getCity());
        assertEquals(parsedNews.getDate(), savedNews.getDate());
        assertEquals(parsedNews.getCases(), savedNews.getCases());
        assertEquals(parsedNews.getDeaths(), savedNews.getDeaths());
        assertEquals(parsedNews.getRecovered(), savedNews.getRecovered());
        verify(newsParser, times(1)).parse(rawText);
        verify(parsedNewsRepository, times(1)).save(parsedNews);
    }

    */
/**
 * Test that the parseAndSave method throws an exception when the parsed news is invalid.
 *//*

    @Test
    void testParseAndSave_InvalidParsedNews() {
        // Arrange
        String rawText = "Invalid news text";
        ParsedNews invalidParsedNews = new ParsedNews();
        invalidParsedNews.setCity(null); // Invalid as city should not be null

        when(newsParser.parse(rawText)).thenReturn(invalidParsedNews);
        when(parsedNewsRepository.save(any())).thenThrow(new IllegalArgumentException("Invalid news data"));

        // Act and Assert
        try {
            parsedNewsService.parseAndSave(rawText);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid news data", e.getMessage());
        }

        verify(newsParser, times(1)).parse(rawText);
        verify(parsedNewsRepository, times(1)).save(invalidParsedNews);
    }

    */
/**
 * Test that the repository is not called when the parsing logic fails.
 *//*

    @Test
    void testParseAndSave_ParsingFails() {
        // Arrange
        String rawText = "Malformed news text";

        when(newsParser.parse(rawText)).thenThrow(new RuntimeException("Parsing failed"));

        // Act and Assert
        try {
            parsedNewsService.parseAndSave(rawText);
        } catch (RuntimeException e) {
            assertEquals("Parsing failed", e.getMessage());
        }

        verify(newsParser, times(1)).parse(rawText);
        verify(parsedNewsRepository, times(0)).save(any());
    }


*/

}
