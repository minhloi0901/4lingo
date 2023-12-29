package com.example.a4lingo.Services;

import com.example.a4lingo.item.WordItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoteService {
    String theJsonString = "["
            + "{"
            + "\"word\": \"Apple\","
            + "\"meaning\": \"A fruit with red, green, or yellow skin and a sweet taste\""
            + "},"
            + "{"
            + "\"word\": \"Book\","
            + "\"meaning\": \"A set of pages that are bound together and written on\""
            + "},"
            + "{"
            + "\"word\": \"Cat\","
            + "\"meaning\": \"A small domesticated carnivorous mammal with soft fur\""
            + "},"
            + "{"
            + "\"word\": \"Dog\","
            + "\"meaning\": \"A domesticated carnivorous mammal that typically has a long snout\""
            + "}"
            + "]";

    public List<WordItem> getListWord(int user_id) {

        // send user_id to server to get list words in json

        return  getWordItemsFromJson(theJsonString);
    }

    public void deleteWord(int user_id, String word) {
        // send user_id and word to server to delete in database
    }

    public List<WordItem> getWordItemsFromJson(String jsonString) {
        List<WordItem> wordItemList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String word = jsonObject.getString("word");
                String meaning = jsonObject.getString("meaning");

                WordItem item = new WordItem(word, meaning);
                wordItemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return wordItemList;
    }
}
