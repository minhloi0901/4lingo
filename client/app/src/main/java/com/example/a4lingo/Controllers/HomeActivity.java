package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.a4lingo.R;

public class HomeActivity extends MainActivity {
    private final String TAG = "HomeActivity";
    @Override
    protected void renderLayout() {
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_home, root, false);

        root.addView(v);
    }
    @Override
    protected void renderNavigation() {
        super.renderNavigation();

        FrameLayout wordMatchingFrame = findViewById(R.id.wordMatchingFrame);
        wordMatchingFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordMatchingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        FrameLayout completeSentenceFrame = findViewById(R.id.completeSentenceFrame);
        completeSentenceFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SentenceTranslationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        FrameLayout multipleChoiceFrame = findViewById(R.id.multipleChoiceFrame);
        multipleChoiceFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FrameLayout conversationFrame = findViewById(R.id.conversationFrame);
        conversationFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConversationExerciseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
