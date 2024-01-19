package com.example.a4lingo.Services;

import android.content.Context;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;
import com.example.a4lingo.item.RankingItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderBoardService {

    Context context;

    public LeaderBoardService(Context _context) {
        this.context = _context;
    }

    public void get_all_users(Utils.Callback callback) {
        // Specify the method as GET and the endpoint as "users"
        DataManager dataManager = new DataManager();
        dataManager.sendRequest(DataManager.GET, "users", null, new Utils.Callback() {
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

    // Helper method to parse the JSON response into a list of RankingItem
    public List<RankingItem> parseUserList(String response) {
        List<RankingItem> userList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);

            // Create a list to store the parsed users before sorting
            List<RankingItem> unsortedList = new ArrayList<>();

            // Parse and create RankingItem objects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userObject = jsonArray.getJSONObject(i);
                RankingItem user = new RankingItem(
                        0, // We'll update this later after sorting
                        userObject.getInt("id"),
                        userObject.getString("username"), // Corrected field name
                        userObject.getInt("score")
                );
                unsortedList.add(user);
            }

            // Sort the list by score in decreasing order
            Collections.sort(unsortedList, new Comparator<RankingItem>() {
                @Override
                public int compare(RankingItem user1, RankingItem user2) {
                    return Integer.compare(user2.getScore(), user1.getScore());
                }
            });

            // Update the rank and add to the final userList
            for (int i = 0; i < unsortedList.size(); i++) {
                RankingItem user = unsortedList.get(i);
                user.rank = (i + 1); // Update rank
                userList.add(user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
