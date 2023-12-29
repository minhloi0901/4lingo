package com.example.a4lingo.Services;

import com.example.a4lingo.item.ContestItem;
import com.google.gson.Gson;

public class CreateContestService {
    public void updateContest(ContestItem contestItem) {
        String contestItemJson = convertContestItemToJson(contestItem);
    }

    private String convertContestItemToJson(ContestItem contestItem) {
        Gson gson = new Gson();
        return gson.toJson(contestItem);
    }
}
