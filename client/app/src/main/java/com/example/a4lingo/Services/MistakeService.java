package com.example.a4lingo.Services;

import com.example.a4lingo.item.MistakeItem;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MistakeService {
    String theStringJson = "["
            + "{"
            + "\"mistake_id\": 1,"
            + "\"assignmentType\": \"Grammar\","
            + "\"mistakeDescription\": \"Incorrect verb tense usage\""
            + "},"
            + "{"
            + "\"mistake_id\": 2,"
            + "\"assignmentType\": \"Vocabulary\","
            + "\"mistakeDescription\": \"Misuse of 'affect' instead of 'effect'\""
            + "},"
            + "{"
            + "\"mistake_id\": 3,"
            + "\"assignmentType\": \"Pronunciation\","
            + "\"mistakeDescription\": \"Incorrect stress on the second syllable of 'record'\""
            + "},"
            + "{"
            + "\"mistake_id\": 4,"
            + "\"assignmentType\": \"Spelling\","
            + "\"mistakeDescription\": \"Misspelled 'necessary' as 'neccessary'\""
            + "}"
            // ... additional mistakes ...
            + "]";

    public List<MistakeItem> getListMistake(int user_id) {

        return getMistakeItemsFromJson(theStringJson);
    }

    public void deleteMistake(int user_id, int mistake_id) {
        // send to server to delete mistake in database
    }

    public List<MistakeItem> getMistakeItemsFromJson(String jsonString) {
        List<MistakeItem> mistakeList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int mistakeId = jsonObject.getInt("mistake_id");
                String assignmentType = jsonObject.getString("assignmentType");
                String mistakeDescription = jsonObject.getString("mistakeDescription");

                MistakeItem item = new MistakeItem(mistakeId, assignmentType, mistakeDescription);
                mistakeList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return mistakeList;
    }
}
