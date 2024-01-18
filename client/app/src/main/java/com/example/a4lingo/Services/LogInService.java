package com.example.a4lingo.Services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInService {
    private Context context;

    // Define a callback interface
    public interface LoginCallback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    public LogInService(Context context) {
        this.context = context;
    }

    public void login(String username, String password, LoginCallback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("username", username);
            jsonParam.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "auth/login", jsonParam, new DataManager.DataManagerCallback() {
            @Override
            public void onSuccess(String response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        // Directly use the callback here to communicate the success
                        callback.onSuccess(jsonResponse.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Directly use the callback here to communicate the failure
                    callback.onFailure(error);
                });
            }
        });
    }
}
