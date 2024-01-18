package com.example.a4lingo.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.a4lingo.Controllers.LoginActivity;
import com.example.a4lingo.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterService {
    private Context context;

    public RegisterService(Context context) {
        this.context = context;
    }

    public void registerUser(String userName, String password, String phoneNumber, String email) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("username", userName);
            jsonParam.put("password", password);
            jsonParam.put("score", 0); // Assuming default value
            jsonParam.put("rating", 0); // Assuming default value
            jsonParam.put("role", "LEARNER");
            if (!phoneNumber.isEmpty()) {
                jsonParam.put("phone_number", phoneNumber);
            }
            if (!email.isEmpty()) {
                jsonParam.put("email", email);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "auth/signup", jsonParam, new Utils.Callback() {
            @Override
            public void onSuccess(String response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.optString("message", "");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                        Log.d("message", message);
                        if ("User created successfully.".equals(message)) {
                            // Intent to navigate to LoginActivity
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show());
            }
        });
    }
}
