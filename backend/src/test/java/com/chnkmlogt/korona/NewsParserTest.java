
package com.chnkmlogt.korona;

import com.chnkmlogt.korona.model.ParsedNews;
import com.chnkmlogt.korona.util.NewsParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NewsParserTest {

    @Test
    public void testParse() {
        String text = "20.04.2020 tarihinde Ankara da Korona virüs salgınında yapılan testlerde 15 yeni vaka bulundu. 1 kişi vefat etti. 5 kişide taburcu oldu.";
        ParsedNews parsed = NewsParser.parse(text);
        assertEquals("Ankara", parsed.getCity());
        assertEquals(LocalDate.of(2020, 4, 20), parsed.getDate());
        assertEquals(15, parsed.getCases());
        assertEquals(1, parsed.getDeaths());
        assertEquals(5, parsed.getRecovered());
    }

    @Test
    public void testParseWithoutDeaths() {
        String text = "20.04.2020 tarihinde İzmir'de Korona vakalarında 30 yeni vaka görüldü. 10 kişi taburcu edildi.";
        ParsedNews parsed = NewsParser.parse(text);
        assertEquals("İzmir", parsed.getCity());
        assertEquals(LocalDate.of(2020, 4, 20), parsed.getDate());
        assertEquals(30, parsed.getCases());
        assertEquals(0, parsed.getDeaths());
        assertEquals(10, parsed.getRecovered());
    }

    @Test
    public void testParseWithoutRecovered() {
        String text = "20.04.2020 tarihinde Adana'da Korona salgınında 50 yeni vaka ve 5 kişi vefat etti.";
        ParsedNews parsed = NewsParser.parse(text);
        assertEquals("Adana", parsed.getCity());
        assertEquals(LocalDate.of(2020, 4, 20), parsed.getDate());
        assertEquals(50, parsed.getCases());
        assertEquals(5, parsed.getDeaths());
        assertEquals(0, parsed.getRecovered());
    }

    @Test
    public void testParseWithDifferentCity() {
        String text = "25.07.2021 tarihinde İstanbul'da Korona virüsü salgını sebebiyle 100 vaka ve 20 ölü rapor edildi. 30 kişi taburcu oldu.";
        ParsedNews parsed = NewsParser.parse(text);
        assertEquals("İstanbul", parsed.getCity());
        assertEquals(LocalDate.of(2021, 7, 25), parsed.getDate());
        assertEquals(100, parsed.getCases());
        assertEquals(20, parsed.getDeaths());
        assertEquals(30, parsed.getRecovered());
    }

    @Test
    public void testParseInvalidDate() {
        String text = "Ankara'da Korona virüste 15 vaka, 2 kişi öldü, 5 kişi iyileşti.";
        ParsedNews parsed = NewsParser.parse(text);
        assertEquals("Ankara", parsed.getCity());
        assertNull(parsed.getDate());
        assertEquals(15, parsed.getCases());
        assertEquals(2, parsed.getDeaths());
        assertEquals(5, parsed.getRecovered());
    }

    @Test
    public void testParsePartialInformation() {
        String text = "22.08.2020 tarihinde yalnızca 1 vaka tespit edildi.";
        ParsedNews parsed = NewsParser.parse(text);
        assertNull(parsed.getCity());
        assertEquals(LocalDate.of(2020, 8, 22), parsed.getDate());
        assertEquals(1, parsed.getCases());
        assertEquals(0, parsed.getDeaths());
        assertEquals(0, parsed.getRecovered());
    }
}
