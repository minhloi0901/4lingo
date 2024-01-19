package com.example.a4lingo.Services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdatePasswordService {
    private final Context context;

    public UpdatePasswordService(Context context) {
        this.context = context;
    }


    public void updatePassword(String token, String old, String new1, Utils.Callback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("token", token);
            jsonParam.put("old_password", old);
            jsonParam.put("new_password", new1);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error creating JSON in WordDictionaryService.");
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON in UpdatePasswordService.", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "profile/update_password", jsonParam, Utils.createCallback(context, callback));
    }
}
