package com.example.a4lingo.Services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterService {

    // 0 - success
    // 1 - invalid email
    // 2 - existed email
    // 3 - ...

    public int registerUser(String userName, String email, String password) {
        String apiUrl = "http://127.0.0.1:5000/auth/signup";

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable input/output streams
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Build the JSON payload for registration
            String jsonInputString = String.format("{\"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}",
                    userName, email, password);

            // Write the JSON payload to the output stream
            connection.getOutputStream().write(jsonInputString.getBytes());

            // Get the HTTP response code
            int responseCode = connection.getResponseCode();

            // Process the response code and return the appropriate result
            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                    // Registration successful
                    return 0;
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    // Invalid email
                    return 1;
                case HttpURLConnection.HTTP_CONFLICT:
                    // Existed email
                    return 2;
                default:
                    // Handle other response codes as needed
                    return 3;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 3; // An exception occurred, consider it as an error
        }
    }
}
