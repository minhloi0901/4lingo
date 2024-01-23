package com.example.a4lingo.data;

import androidx.annotation.NonNull;

import com.example.a4lingo.Services.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;

public class DataManager {
    private String url = "http://10.0.2.2:5000"; // Removed the last slash
    public static final String POST = "POST";    // Made constants 'static final'
    public static final String GET = "GET";

//    public interface DataManagerCallback {
//        void onSuccess(String response);
//        void onFailure(String error);
//    }

    public DataManager() {
        // Removed context as it's not used
    }

    public void sendRequest(String type, String method, JSONObject jsonParam, Utils.Callback callback) {
        String fullURL = url + "/" + method; // Ensured no double slashes

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS).build();

        Request request;
        if (type.equals(POST)) {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(jsonParam.toString(), JSON);
            request = new Request.Builder()
                    .url(fullURL)
                    .post(body)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(fullURL)
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().string());
                } else {
                    try {
                        assert response.body() != null;
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String error_message = jsonObject.getString("message");
                        callback.onFailure(error_message);
                    } catch (JSONException e) {
                        callback.onFailure(response.message());
                    }
                }
            }
        });
    }
}
