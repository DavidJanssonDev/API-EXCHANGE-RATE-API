package org.example;

import org.helpers.FileReaderHelper;
import org.httpApiClasses.HttpsClientExtension;

import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class



Main {
    static void main() {
        System.out.println("=== Test 1: FileReaderHelper ===");
        HashMap<String, String> settings = FileReaderHelper.readFromFile("API_SETTINGS");

        settings.forEach((key, value) ->
                System.out.println("Key: " + key + " | Value: " + value)
        );

        // Test 2: Skapa HttpsClientExtension via fil
        System.out.println("\n=== Test 2: HttpsClientExtension.fromFile ===");
        HttpsClientExtension client = HttpsClientExtension.fromFile("API_SETTINGS");
        System.out.println("Client skapad: " + client);

        // Test 3: Felhantering - fil som inte finns
        System.out.println("\n=== Test 3: Felhantering ===");
        try {
            HashMap<String, String> bad = FileReaderHelper.readFromFile("FINNS_INTE");
        } catch (RuntimeException e) {
            System.out.println("Förväntat fel: " + e.getMessage());
        }

        System.out.println("\nAlla tester klara!");
    }
}
