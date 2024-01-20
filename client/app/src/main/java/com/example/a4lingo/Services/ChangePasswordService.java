package com.example.a4lingo.Services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.example.a4lingo.Controllers.LoginActivity;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.a4lingo.data.DataManager;

public class ChangePasswordService {
    private Context context;

    public ChangePasswordService(Context context) {
        this.context = context;
    }

    public void changePass(String email, String password) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
            jsonParam.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON exception, maybe show a toast
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "auth/change_password", jsonParam, new Utils.Callback() {
            @Override
            public void onSuccess(String response) {
                // Run on UI thread to handle UI operations
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Change password successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                // Run on UI thread to handle UI operations
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Failed to change password: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
