package com.example.a4lingo.Services;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LogInService {
    public boolean login(String userName, String password) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }

        String apiUrl = "http://10.0.2.2:5000//auth/login";
        String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", userName, password);

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable output streams
            connection.setDoOutput(true);

            System.out.println("InputString: " + jsonInputString);

            // Build the JSON payload for authentication
            connection.getOutputStream().write(jsonInputString.getBytes());

            // Get the HTTP response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Message: " + connection.getResponseMessage());

            // Check if the authentication was successful (you need to adjust this based on your API response)
            if (responseCode == 200) {
                // Authentication successful
                return true;
            } else {
                // Authentication failed
                // Print error message returned from server
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONObject errorJson = new JSONObject(response.toString());
                    Log.e("LOGIN", "Error Response: " + errorJson.getString("message"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        }
        catch (IOException e){
            Log.i("LOGIN", "login: IOException");
            e.printStackTrace();
            return false;
        }
    }
}

