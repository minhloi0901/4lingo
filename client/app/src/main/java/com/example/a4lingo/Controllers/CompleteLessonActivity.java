package com.example.a4lingo.Controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.CompleteLessonService;
import com.example.a4lingo.Services.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class CompleteLessonActivity extends OneTopNavActivity {
    int totalScore = 0;
    float theTime = 0;
    float theAccuracy = 0;

    private String lessonName = "";
    private int lesson_id = 0;
    private int user_id = 0;
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Kết quả bài học", null);
        renderNavigation();
    }

    @Override
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_complete_lesson, root, false);
        createSampleData();
        getIntentData();
        renderAnInstance(v);
        root.addView(v);
    }

    private void createSampleData() {
        totalScore = 50;
        theTime = 80;
        theAccuracy = 80;
        lessonName = "Quê em mùa nước lũ";
    }

    private void getIntentData() {
        Intent intent = getIntent(); // Get the Intent that started this activity

        // Check if the Intent has the extra keys for score, time, accuracy, and name
        if (!intent.hasExtra("SCORE") || !intent.hasExtra("TIME") ||
                !intent.hasExtra("ACCURACY") || !intent.hasExtra("TYPE")) {
            // If any of the keys are missing, show a long Toast message
            Toast.makeText(this, "Lack of results", Toast.LENGTH_LONG).show();
        } else {
            // All keys are present, retrieve the values
            totalScore = intent.getIntExtra("SCORE", 0); // Retrieve the score value
            theTime = intent.getFloatExtra("TIME", 0f); // Retrieve the time value
            theAccuracy = intent.getFloatExtra("ACCURACY", 0.0f); // Retrieve the accuracy value
            type = intent.getIntExtra("TYPE", 1);
//            lessonName = intent.getStringExtra("LESSON_NAME"); // Retrieve the name string
//            lesson_id = intent.getIntExtra("LESSON_ID", 0);
            // Use the score, time, accuracy, and name as needed in your activity
        }
        if (intent.hasExtra("USER_ID")) {
            user_id = intent.getIntExtra("USER_ID", 0);
        }
    }

    @SuppressLint("SetTextI18n")
    private void renderAnInstance(View v) {
        TextView score = v.findViewById(R.id.score);
        TextView time = v.findViewById(R.id.time);
        TextView accuracy = v.findViewById(R.id.accuracy);
        TextView resultText = v.findViewById(R.id.resultText);
        ImageView img = v.findViewById(R.id.resultImg);

        score.setText(String.valueOf(totalScore));
        time.setText(String.valueOf(theTime) + " mins");
        accuracy.setText(String.valueOf(theAccuracy) + "%");

        if (theAccuracy >= 80) {
            resultText.setText("Bạn quá giỏi!");
            img.setImageResource(R.drawable.happy_result);
        }
        else if (theAccuracy >= 50) {
            resultText.setText("Vậy là tốt rồi!");
            img.setImageResource(R.drawable.happy_result);
        }
        else {
            resultText.setText("Phải cố gắng hơn mới được!");
            img.setImageResource(R.drawable.sad_result);
        }
    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();

        ImageView goBackButton = findViewById(R.id.leftButton);
        if (goBackButton != null){
            goBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }


        Button btn1 = findViewById(R.id.resultReviewButton);
        Button btn2 = findViewById(R.id.resultContinueButton);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteLessonActivity.this, ReviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("LESSON_NAME", lessonName);
                intent.putExtra("LESSON_ID", lesson_id);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateResult()){
                    Intent intent = new Intent(CompleteLessonActivity.this, ShareActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(CompleteLessonActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean updateResult() {
        if (theAccuracy < 50){
            Toast.makeText(getApplicationContext(), "Not passed!", Toast.LENGTH_SHORT).show();
            return false;
        }

        CompleteLessonService completeLessonService = new CompleteLessonService(getApplicationContext());
        String token = Utils.getToken(getApplicationContext());
        if (token != null){
            completeLessonService.updateLessonResult(token, totalScore, type, new Utils.Callback() {
                public void onSuccess(String response) {
                    runOnUiThread( () -> {
                        try {
                            System.out.println(response);
                            JSONObject jsonResponse = new JSONObject(response);
                            String message = jsonResponse.getString("message");

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            System.out.println("Error parsing JSON in CompleteLessonActivity");
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON in CompleteLessonActivity", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }

        return false;
    }
}