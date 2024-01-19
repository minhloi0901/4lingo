package com.example.a4lingo.Services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;
import com.example.a4lingo.item.WordItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoteService {
    private final Context context;

    public NoteService(Context context) {
        this.context = context;
    }

    public void getAllWords(String token, Utils.Callback callback) {
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
        dataManager.sendRequest("POST", "vocabularies/get_all_vocab", jsonParam, Utils.createCallback(context, callback));
    }

    public List<WordItem> parseJsonResponse(JSONArray jsonResponse) {
        List<WordItem> wordItemList = new ArrayList<>();

        try {
            // Assuming that the words are contained in a JSON array with a specific key
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject wordObject = jsonResponse.getJSONObject(i);

                // Extracting values from the JSON object
                int id = wordObject.getInt("id");
                String meaning = wordObject.getString("meaning");
                String text = wordObject.getString("text");

                // Creating a WordItem object and adding it to the list
                WordItem wordItem = new WordItem(text, meaning);
                wordItemList.add(wordItem);
            }
        } catch (JSONException e) {
            e.printStackTrace(); // Handle JSON parsing exception here
        }

        return wordItemList;
    }

    public void deleteAWord(String token, String word, Utils.Callback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("token", token);
            jsonParam.put("text", word);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error creating JSON in WordDictionaryService.");
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON in NoteService.", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "vocabularies/delete", jsonParam, Utils.createCallback(context, callback));
    }
}
