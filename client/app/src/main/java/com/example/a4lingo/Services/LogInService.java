package com.example.a4lingo.Services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LogInService {
    public boolean login(String userName, String password) {
        String apiUrl = "http://127.0.0.1:5000/auth/login";

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable input/output streams
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Build the JSON payload for authentication
            String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", userName, password);

            // Write the JSON payload to the output stream
            connection.getOutputStream().write(jsonInputString.getBytes());

            // Get the HTTP response code
            int responseCode = connection.getResponseCode();

            // Check if the authentication was successful (you need to adjust this based on your API response)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Authentication successful
                return true;
            } else {
                // Authentication failed
                return false;
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
