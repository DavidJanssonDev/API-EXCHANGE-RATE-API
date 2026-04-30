package org.core.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FileReaderHelper {

    public static HashMap<String, String> readFromFile(String fileName) {
        HashMap<String, String> settings = new HashMap<>();

        try (
                InputStream is = FileReaderHelper.class.getClassLoader().getResourceAsStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))
        ) {
            String fileLine;
            while ((fileLine = reader.readLine()) != null) {
                if (fileLine.isBlank() || fileLine.startsWith("#")) continue;

                String[] parts = fileLine.split("=", 2);
                if (parts.length == 2) {
                    String key   = parts[0].trim();
                    String value = parts[1].trim().replaceAll("^\"|\"$", "");
                    settings.put(key, value);
                }
            }

        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Could not read settings file: " + fileName, e);
        }

        return settings;
    }
}
