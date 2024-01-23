package com.example.a4lingo.Services;

import android.content.Context;

import com.example.a4lingo.R;
import com.example.a4lingo.data.DataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.a4lingo.item.AchievementItem;

import java.util.ArrayList;
import java.util.List;

public class AchievementService {

    Context context;

    public AchievementService(Context _context) {
        this.context = _context;
    }

    public void getAchievements(Utils.Callback callback, String token) {
        // Specify the method as POST and the endpoint as "get_user_achievements"
        DataManager dataManager = new DataManager();

        // Create a JSON object with the "token" key and its associated data
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send the POST request with the JSON body
        dataManager.sendRequest(DataManager.POST, "achievements/get_user_achievements", jsonBody, new Utils.Callback() {
            @Override
            public void onSuccess(String response) {
                // Handle the successful response here
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(String error) {
                // Handle the failure here
                callback.onFailure(error);
            }
        });
    }
    public List<AchievementItem> parseAchievementItemsFromJson(String jsonResponse) {
        List<AchievementItem> achievementItemList = new ArrayList<>();

        try {
            JSONObject jsonRootObject = new JSONObject(jsonResponse);
            JSONArray achievementsArray = jsonRootObject.getJSONArray("achievements");

            for (int i = 0; i < achievementsArray.length(); i++) {
                JSONObject achievementObject = achievementsArray.getJSONObject(i);

                // Extract values from JSON and assign default values
                String name = achievementObject.optString("name", "Default Name");
                String content = achievementObject.optString("content", "Default Description");
                int progress = 1;
                int total = 3;
                int imageResourceId = R.drawable.ic_achievement_instance; // Replace with the actual resource ID

                AchievementItem achievementItem = new AchievementItem(name, content, progress, total, imageResourceId);
                achievementItemList.add(achievementItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return achievementItemList;
    }
}
