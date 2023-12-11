package com.example.a4lingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        renderLayout();
        renderNavigation();
    }

    protected void renderLayout() {

    }
    protected void renderNavigation() {
        ImageView leaderboardView = findViewById(R.id.leaderboard);
        leaderboardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaderBoardActivity.class);
                startActivity(intent);
            }
        });

        ImageView contestView = findViewById(R.id.contest);
        contestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContestActivity.class);
                startActivity(intent);
            }
        });

        ImageView notificationView = findViewById(R.id.notification);
        notificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageView profileView = findViewById(R.id.profile);
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        FrameLayout homeView = findViewById(R.id.home);
        homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        FrameLayout searchView = findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        FrameLayout mistakeView = findViewById(R.id.mistake);
        mistakeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MistakeActivity.class);
                startActivity(intent);
            }
        });
        FrameLayout noteView = findViewById(R.id.note);
        noteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intent);
            }
        });
    }
}