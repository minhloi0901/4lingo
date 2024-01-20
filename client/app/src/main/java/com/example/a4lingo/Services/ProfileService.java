package com.example.a4lingo.Services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;
import com.example.a4lingo.item.ProfileItem;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileService{

    private Context context;
    public ProfileService(Context context) {
        this.context = context;
    }

    public void getProfileItem(String token, Utils.Callback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error creating JSON in WordDictionaryService.");
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON in NoteService.", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "profile", jsonParam, Utils.createCallback(context, callback));
    }

    public void saveProfile(String token, ProfileItem profileItem, Utils.Callback callback) {
        JSONObject jsonParam = new JSONObject();

        try {
            jsonParam.put("token", token);
            jsonParam.put("email", profileItem.getEmail());
            jsonParam.put("username", profileItem.getLoginName());
            jsonParam.put("phone_number", profileItem.getPhoneNumber());
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error creating JSON in ProfileService.");
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON in ProfileService.", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "profile/update", jsonParam, Utils.createCallback(context, callback));
    }

    public ProfileItem parseJsonReponse(JSONObject jsonResponse) {
        ProfileItem sampleProfile = new ProfileItem();
//        sampleProfile.setUserName("John Doe");

        try {
            sampleProfile.setLoginName(jsonResponse.getString("username"));
            sampleProfile.setEmail(jsonResponse.getString("email"));
            sampleProfile.setPassword(jsonResponse.getString("password"));
            sampleProfile.setPhoneNumber(jsonResponse.getString("phone_number"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return sampleProfile;
    }
}
