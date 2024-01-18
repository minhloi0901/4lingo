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

    public LogInService(Context context) {
        this.context = context;
    }

    public void login(String username, String password, Utils.Callback callback) {
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
        dataManager.sendRequest("POST", "auth/login", jsonParam, Utils.createCallback(context, callback));
    }
}

