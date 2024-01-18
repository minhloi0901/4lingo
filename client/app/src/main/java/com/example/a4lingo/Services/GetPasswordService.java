package com.example.a4lingo.Services;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.a4lingo.data.DataManager;

public class GetPasswordService {
    private Context context;

    public interface EmailResponseCallback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    public GetPasswordService(Context context) {
        this.context = context;
    }

    public void sendEmail(String email, EmailResponseCallback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON exception, maybe show a toast
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "auth/reset_password", jsonParam, new Utils.Callback() {
            @Override
            public void onSuccess(String response) {
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }
}
