package com.example.a4lingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {

    String achieveName = "";
    String theDescription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        createSampleData();
        getIntentData();
        showData();
        setNavigation();
    }

    private void showData() {
        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);

        // Construct the text for text1 with the achievement name in bold
        String baseText = "Chúc mừng bạn đã đạt được thành tích ";
        String boldText = achieveName;
        String fullText = baseText + boldText;
        SpannableString spannableString = new SpannableString(fullText);

        // Apply the StyleSpan for Bold Typeface for the achievement name
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), baseText.length(), fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        text1.setText(spannableString);

        // Set the description text
        text2.setText(theDescription);
    }

    private void getIntentData() {
        Intent intent = getIntent(); // Get the Intent that started this activity

        // Check for and retrieve the "name" string
        if (intent.hasExtra("name")) {
            achieveName = intent.getStringExtra("name");
        } else {
            Toast.makeText(this, "Lack of achievement name", Toast.LENGTH_LONG).show();
        }

        // Check for and retrieve the "description" string
        if (intent.hasExtra("description")) {
            theDescription = intent.getStringExtra("description");
        } else {
            Toast.makeText(this, "Lack of achievement description", Toast.LENGTH_LONG).show();
        }
    }


    private void createSampleData() {
        achieveName = "Quán Quân";
        theDescription = "Đạt hạng #1 ở bảng xếp hạng bất kỳ";
    }

    private void setNavigation() {
        TextView off = findViewById(R.id.shareOff);
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


    }
}