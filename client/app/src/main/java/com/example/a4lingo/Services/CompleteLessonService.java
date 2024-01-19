package com.example.a4lingo.Services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class CompleteLessonService {

    private final Context context;

    public CompleteLessonService(Context context) {
        this.context = context;
    }

    public void updateLessonResult(String token, int total_score, int type, Utils.Callback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("token", token);
            jsonParam.put("total_score", total_score);
            jsonParam.put("type", type);
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
