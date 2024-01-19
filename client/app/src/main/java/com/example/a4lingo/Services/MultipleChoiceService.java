package com.example.a4lingo.Services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;
import com.example.a4lingo.item.MultipleChoiceQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleChoiceService {
    private Context context;
    public MultipleChoiceService(Context context) {
        this.context = context;
    }

    public void getMultipleChoiceQuestions(String token, int type, Utils.Callback callback) {
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

    public List<MultipleChoiceQuestion> parseJsonResponse(JSONArray jsonResponse) throws JSONException {
        List<MultipleChoiceQuestion> questions = new ArrayList<>();
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject jsonObject = jsonResponse.getJSONObject(i);

            List<String> choices = new ArrayList<>();
            String[] choiceArray = jsonObject.getString("choice").split("/ ");
            for (String choice : choiceArray) {
                choices.add(choice.trim());
            }

            MultipleChoiceQuestion question = new MultipleChoiceQuestion(
                    jsonObject.getString("content"),
                    choices,
                    choices.indexOf(jsonObject.getString("answer")),
                    jsonObject.getInt("score")
            );

            questions.add(question);
        }

        return questions;
    }
}
