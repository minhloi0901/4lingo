//package com.example.a4lingo.Services;
//
//import com.example.a4lingo.item.WordItem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WordDictionaryService {
//    public String getSpellingPronunciation(String word) {
//        return "/ˈlɪŋɡəʊ/";
//    }
//
//    public List<WordItem> getWordItemList(String word) {
//        List<WordItem> wordItemList = new ArrayList<>();
//        wordItemList.add(new WordItem(word, "1. ngoại ngữ", "\u2022" + " If you live abroad it helps to know the local lingo.", "Nếu bạn sống ở nước ngoài, việc biết biệt ngữ địa phương sẽ giúp ích."));
//        wordItemList.add(new WordItem(word, "2. ngoại ngữ", "\u2022" + " If you live abroad it helps to know the local lingo.", "Nếu bạn sống ở nước ngoài, việc biết biệt ngữ địa phương sẽ giúp ích."));
//        return wordItemList;
//    }
//}
//

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

public class WordDictionaryService {
    private Context context;

    public WordDictionaryService(Context context) {
        this.context = context;
    }

    public void searchWord(String word, Utils.Callback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("word", word);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error creating JSON in WordDictionaryService.");
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON in WordDictionaryService.", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("GET", "dictionary/search?word=" + word, jsonParam, Utils.createCallback(context, callback));
    }

    public List<WordItem> parseJsonResponse(JSONObject jsonResponse) {
        List<WordItem> wordItemList = new ArrayList<>();

        try {
            String word = jsonResponse.getString("word");

            JSONArray phoneticsArray = jsonResponse.getJSONArray("phonetics");
            String pronunciation = getFirstPhoneticPronunciation(phoneticsArray);
            String audioUrl = getFirstPhoneticAudioUrl(phoneticsArray);

            JSONArray meaningsArray = jsonResponse.getJSONArray("meanings");
            for (int i = 0; i < meaningsArray.length(); i++) {
                JSONObject meaningObject = meaningsArray.getJSONObject(i);

                String definition = meaningObject.getString("definition");
                String example = meaningObject.getString("example");
                String translatedText = meaningObject.getString("translatedText");

                WordItem wordItem = new WordItem(word, definition, pronunciation, audioUrl, example, translatedText);
                wordItemList.add(wordItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wordItemList;
    }

    private String getFirstPhoneticPronunciation(JSONArray phoneticsArray) throws JSONException {
        return phoneticsArray.getJSONObject(0).getString("text");
    }

    private String getFirstPhoneticAudioUrl(JSONArray phoneticsArray) throws JSONException {
        return phoneticsArray.getJSONObject(0).getString("audio");
    }

    public void addNote(String token, String word, String meaning, Utils.Callback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("token", token);
            jsonParam.put("text", word);
            jsonParam.put("meaning", meaning);

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error creating JSON in WordDictionaryService.");
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON in WordDictionaryService.", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "vocabularies/add", jsonParam, Utils.createCallback(context, callback));
    }
}
