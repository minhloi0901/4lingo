package com.example.a4lingo.Services;

import okhttp3.*;

import java.io.IOException;

public class LogInService {
    private final OkHttpClient client = new OkHttpClient();

    public void login(String username, String password) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://http://127.0.0.1:5000")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }
}