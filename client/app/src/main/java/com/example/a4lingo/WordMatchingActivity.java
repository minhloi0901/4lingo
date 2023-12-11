package com.example.a4lingo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class WordMatchingActivity extends HomeActivity {
    protected void renderLayout() {
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.root);
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
    }
}
