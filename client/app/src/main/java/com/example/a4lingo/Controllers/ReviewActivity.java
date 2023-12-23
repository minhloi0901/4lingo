package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lingo.R;

public class ReviewActivity extends OneTopNavActivity {
    String lesson_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Đánh giá bài học", null);
        renderNavigation();
    }

    @Override
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_review, root, false);
        
        createSampleData();
        getIntentData();
        renderAnInstance(v);
        root.addView(v);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("lessonName")) {
            lesson_name = intent.getStringExtra("lessonName");
        } else {
            Toast.makeText(this, "Không có bài học nào", Toast.LENGTH_LONG).show();
        }
    }

    private void createSampleData() {
        lesson_name = "Miêu tả làng quê, làm quen thì quá khứ";
    }

    private void renderAnInstance(View v) {
        TextView title = v.findViewById(R.id.lessonTitleReview);
        title.setText(lesson_name);
    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();

        Button continueButton = findViewById(R.id.reviewContinueButton);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        EditText editText = findViewById(R.id.reviewText);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                int ratingInt = (int) rating;
                String review = String.valueOf(editText.getText());

                if (ratingInt == 0) {
                    Toast.makeText(getApplicationContext(), "Hãy đánh giá chất lượng bài học", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Send ratingInt and review to server
                    // BEGIN

                    // END

                    Toast.makeText(getApplicationContext(), "Chúng tôi đã ghi nhận đánh giá của bạn", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ReviewActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}