package main.utility;

import java.io.*;
import java.util.*;

public class EnvLoader {
    public static void main(String[] args) {
        Map<String, String> envVars = loadEnvFile(".env");
    }

    public static Map<String, String> loadEnvFile(String filePath) {
        Map<String, String> envVars = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue; // Ignore empty lines and comments
                }
                String[] keyValue = line.split("=", 2);
                if (keyValue.length == 2) {
                    envVars.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return envVars;
    }
}
