package com.example.a4lingo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CompleteLessonActivity extends OneTopNavActivity {

    int totalScore = 0;
    int theTime = 0;
    int theAccuracy = 0;

    String lessonName = "";

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
        if (!intent.hasExtra("score") || !intent.hasExtra("time") ||
                !intent.hasExtra("accuracy") || !intent.hasExtra("name")) {
            // If any of the keys are missing, show a long Toast message
            Toast.makeText(this, "Lack of results", Toast.LENGTH_LONG).show();
        } else {
            // All keys are present, retrieve the values
            int score = intent.getIntExtra("score", 0); // Retrieve the score value
            int time = intent.getIntExtra("time", 0); // Retrieve the time value
            int accuracy = intent.getIntExtra("accuracy", 0); // Retrieve the accuracy value
            String name = intent.getStringExtra("name"); // Retrieve the name string

            // Use the score, time, accuracy, and name as needed in your activity
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

        Button btn1 = findViewById(R.id.resultReviewButton);
        Button btn2 = findViewById(R.id.resultContinueButton);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteLessonActivity.this, ReviewActivity.class);
                intent.putExtra("lessonName", lessonName);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteLessonActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}