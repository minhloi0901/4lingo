package com.example.a4lingo.data;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataManager extends AppCompatActivity {
    private String url = "http://" + "127.0.0.1" + ":" + 5000 + "/";
    private JSONObject jsonBody; // JSON object for request body
    private MediaType mediaType;
    private RequestBody requestBody;

    private RequestBody buildRequestBody(JSONObject jsonBody) {
        this.jsonBody = jsonBody;
        mediaType = MediaType.parse("application/json; charset=utf-8"); // Set JSON content type
        requestBody = RequestBody.create(jsonBody.toString(), mediaType);
        return requestBody;
    }

    public void postRequest(JSONObject jsonData, String URL) {
        RequestBody requestBody = buildRequestBody(jsonData);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(URL)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Something went wrong:" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(getApplicationContext(), response.body().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}


