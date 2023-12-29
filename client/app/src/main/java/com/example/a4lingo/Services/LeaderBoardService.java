package com.example.a4lingo.Services;

import com.example.a4lingo.item.RankingItem;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoardService {
    String theJsonString = "["
            + "{"
            + "\"rank\": 1,"
            + "\"avatarResId\": 1001,"
            + "\"name\": \"John Doe\","
            + "\"score\": 1500"
            + "},"
            + "{"
            + "\"rank\": 2,"
            + "\"avatarResId\": 1002,"
            + "\"name\": \"Jane Smith\","
            + "\"score\": 1450"
            + "},"
            + "{"
            + "\"rank\": 3,"
            + "\"avatarResId\": 1003,"
            + "\"name\": \"Alice Johnson\","
            + "\"score\": 1400"
            + "},"
            + "{"
            + "\"rank\": 4,"
            + "\"avatarResId\": 1004,"
            + "\"name\": \"Bob Brown\","
            + "\"score\": 1350"
            + "}"
            // ... additional ranking items ...
            + "]";

    public List<RankingItem> getRakingList() {

        return getRankingItemsFromJson(theJsonString);
    }

    public List<RankingItem> getRankingItemsFromJson(String jsonString) {
        List<RankingItem> rankingItemList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int rank = jsonObject.getInt("rank");
                int avatarResId = jsonObject.getInt("avatarResId");
                String name = jsonObject.getString("name");
                int score = jsonObject.getInt("score");

                RankingItem item = new RankingItem(rank, avatarResId, name, score);
                rankingItemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return rankingItemList;
    }
}
