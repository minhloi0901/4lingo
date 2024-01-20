package com.example.a4lingo.Services;

import com.example.a4lingo.item.AchievementItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AchivementService {
    String theJsonString = "["
            + "{"
            + "\"name\": \"Chúa tể nhiệm vụ\","
            + "\"description\": \"Hoàn thành 10 nhiệm vụ\","
            + "\"progress\": 5,"
            + "\"total\": 10,"
            + "\"imageResourceId\": 0"
            + "},"
            + "{"
            + "\"name\": \"Thợ săn đêm\","
            + "\"description\": \"Hoàn thành 3 bài học sau 10 giờ tối\","
            + "\"progress\": 1,"
            + "\"total\": 3,"
            + "\"imageResourceId\": 0"
            + "}"
            + "]";

    public List<AchievementItem> getAchivements(int user_id) {

        // send user_id to server to get achivements

        return getAchievementsFromJson(theJsonString);
    }

    public List<AchievementItem> getAchievementsFromJson(String jsonString) {
        List<AchievementItem> achievementList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                int progress = jsonObject.getInt("progress");
                int total = jsonObject.getInt("total");
                int imageResourceId = jsonObject.getInt("imageResourceId");

                AchievementItem item = new AchievementItem(name, description, progress, total, imageResourceId);
                achievementList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return achievementList;
    }
}
