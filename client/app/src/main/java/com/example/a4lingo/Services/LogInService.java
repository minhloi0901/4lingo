package com.example.a4lingo.Services;

import com.example.a4lingo.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInService {
    public void login(String username, String password) {
        String url = "http://127.0.0.1:5000/login"; // Login endpoint URL
        try {
            // Create a JSON object with username and password
            JSONObject loginData = new JSONObject();
            loginData.put("username", username);
            loginData.put("password", password);

            // Make a POST request with the JSON data
            DataManager dataManager = new DataManager();
            dataManager.postRequest(loginData, url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
