package com.example.a4lingo.Services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictionaryService {
    private Context context;

    public List<String> getSuggestWords(String userID, String searchingText) {
        return Arrays.asList("Hello", "Every", "One");
    }

    public interface DictionaryCallBack {
        void onSuccess(String response);
        void onFailure(String error);
    }

    public DictionaryService(Context context) {
        this.context = context;
    }


//    public void getSuggestWords(String userID, String searchingText, DictionaryCallBack callback) {
//        JSONObject jsonPram = new JSONObject();
//        try{
//            jsonPram.put("word", searchingText);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            new Handler(Looper.getMainLooper()).post(() ->
//                    Toast.makeText(context, "Error creating JSON", Toast.LENGTH_SHORT).show());
//            return;
//        }
//
//        DataManager dataManager = new DataManager();
//        dataManager.sendRequest("POST", "dictionary/search", jsonPram, new DataManager.DataManagerCallback() {
//            @Override
//            public void onSuccess(String response) {
//                new Handler(Looper.getMainLooper()).post(() -> {
//                    try {
//                        JSONObject jsonResponse = new JSONObject(response);
//                        // Directly use the callback here to communicate the success
//                        callback.onSuccess(jsonResponse.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(String error) {
//                new Handler(Looper.getMainLooper()).post(() -> {
//                    // Directly use the callback here to communicate the failure
//                    callback.onFailure(error);
//                });
//            }
//        });
//    }


}
