package org.helpers;

import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderHelperTest {

    @Test
    void testReadsAllValues() {
        HashMap<String, String> settings = FileReaderHelper.readFromFile("API_SETTINGS");

        assertNotNull(settings);
        assertEquals(2, settings.size(), "Ska innehålla exakt 2 rader");
    }

    @Test
    void testApiKeyExists() {
        HashMap<String, String> settings = FileReaderHelper.readFromFile("API_SETTINGS");

        assertTrue(settings.containsKey("api_key"));
        assertFalse(settings.get("api_key").isBlank());
    }

    @Test
    void testBaseApiLinkExists() {
        HashMap<String, String> settings = FileReaderHelper.readFromFile("API_SETTINGS");

        assertTrue(settings.containsKey("base_api_link"));
        assertFalse(settings.get("base_api_link").isBlank());
    }

    @Test
    void testNonExistentFileThrowsException() {
        assertThrows(RuntimeException.class, () ->
                FileReaderHelper.readFromFile("FINNS_INTE")
        );
    }
}