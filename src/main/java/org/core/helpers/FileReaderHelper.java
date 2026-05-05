package org.core.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Utility for reading simple {@code key=value} configuration files from the
 * classpath.
 *
 * <p>Format rules:</p>
 * <ul>
 *   <li>One entry per line: {@code KEY=value} or {@code KEY="value"}</li>
 *   <li>Lines that are blank or start with {@code #} are skipped.</li>
 *   <li>The {@code =} sign is the delimiter; only the first occurrence splits
 *       key from value (allowing {@code =} inside values).</li>
 *   <li>Surrounding double-quotes on the value are stripped automatically.</li>
 * </ul>
 *
 * <p>Example file ({@code API_SETTINGS} in {@code src/main/resources/}):</p>
 * <pre>
 * # Exchange Rate API settings
 * API_KEY="your-api-key-here"
 * BASE_URL="https://v6.exchangerate-api.com/v6"
 * </pre>
 *
 * <p>Usage:</p>
 * <pre>{@code
 * HashMap<String, String> settings = FileReaderHelper.readFromFile("API_SETTINGS");
 * String key = settings.get("API_KEY");
 * }</pre>
 */
public class FileReaderHelper {

    /**
     * Reads all {@code key=value} pairs from a classpath resource file.
     *
     * @param fileName the classpath-relative file name from the resource folder inside the main folder (no leading slash),
     *                 e.g. {@code "API_SETTINGS"}
     * @return a {@link HashMap} mapping trimmed keys to trimmed, unquoted values;
     *         never {@code null}, but may be empty if the file contains no valid pairs
     * @throws RuntimeException wrapping an {@link IOException} or
     *                          {@link NullPointerException} if the file cannot be
     *                          found or read
     */
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
