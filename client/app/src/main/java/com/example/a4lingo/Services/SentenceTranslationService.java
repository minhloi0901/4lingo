package com.example.a4lingo.Services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;
import com.example.a4lingo.item.MultipleChoiceQuestion;
import com.example.a4lingo.item.TranslationQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SentenceTranslationService {
    private final Context context;

    public SentenceTranslationService(Context context) {
        this.context = context;
    }

    public void getTranslationQuestions(String token, int type, Utils.Callback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("token", token);
            jsonParam.put("lesson_type", type);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error creating JSON in MultipleChoiceService.");
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON in MultipleChoiceService.", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "lessons/find", jsonParam, Utils.createCallback(context, callback));
    }

    public List<TranslationQuestion> parseJsonResponse(JSONArray jsonResponse) throws JSONException {
        List<TranslationQuestion> questions = new ArrayList<>();

        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject jsonObject = jsonResponse.getJSONObject(i);

            String[] choiceArray = jsonObject.getString("choice").split("/");
            List<String> choices = new ArrayList<>(Arrays.asList(choiceArray));
            Collections.shuffle(choices);

            TranslationQuestion question = new TranslationQuestion(
                    jsonObject.getString("content"),
                    jsonObject.getString("answer"),
                    choices,
                    jsonObject.getInt("score")
            );

            questions.add(question);
        }

        return questions;
    }
}
