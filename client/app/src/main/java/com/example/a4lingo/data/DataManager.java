package com.example.a4lingo.data;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;

public class DataManager {
    private static DataManager instance;
    private OkHttpClient okHttpClient;

    private DataManager() {
        okHttpClient = new OkHttpClient();
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void postRequest(JSONObject jsonData, String endpoint, DataManagerCallback callback) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(jsonData.toString(), mediaType);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(endpoint)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Something went wrong: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    callback.onResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure("Error while processing the response.");
                }
            }
        });
    }

    public interface DataManagerCallback {
        void onResponse(String response);
        void onFailure(String error);
    }
}
