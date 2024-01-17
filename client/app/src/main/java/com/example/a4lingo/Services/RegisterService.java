package com.example.a4lingo.Services;

import com.example.a4lingo.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterService {

    public interface RegistrationCallback {
        void onRegistrationSuccess(String message);
        void onRegistrationFailure(String error);
    }

    public void registerUser(String userName, String email, String password, RegistrationCallback callback) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", userName);
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            DataManager.getInstance().postRequest(jsonBody, "http://127.0.0.1:5000/auth/signup", new DataManager.DataManagerCallback() {
                @Override
                public void onResponse(String response) {
                    callback.onRegistrationSuccess(response);
                }

                @Override
                public void onFailure(String error) {
                    callback.onRegistrationFailure(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onRegistrationFailure("An error occurred while creating the JSON request.");
        }
    }
}
