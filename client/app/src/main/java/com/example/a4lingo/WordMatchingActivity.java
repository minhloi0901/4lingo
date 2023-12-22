package com.example.a4lingo;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WordMatchingActivity extends MainActivity {
    protected void renderLayout() {
        super.renderLayout();
        Log.i("H", "WordMatchingActivity");
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_word_matching, root, false);
        root.addView(v);
    }
    protected void renderNavigation() {
        super.renderNavigation();

        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        ImageView clock = findViewById(R.id.clockImageView);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompleteLessonActivity.class);
                startActivity(intent);
            }
        });
    }
}
