package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lingo.R;

public class WordMatchingActivity extends MainActivity {
    private CountDownTimer countDownTimer;
    private long startTimeMillis;
    private long timeLeftInMillis = 90*1000;
    private TextView timerTextView;
    protected void renderLayout() {
        super.renderLayout();

        Log.i("H", "WordMatchingActivity");
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_word_matching, root, false);

        timerTextView = v.findViewById(R.id.timerTextView);
        startTimeMillis = SystemClock.elapsedRealtime();
        startCountDownTimer();

        root.addView(v);
    }
    protected void renderNavigation() {
        super.renderNavigation();

        View backButton = findViewById(R.id.ignoreButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

//        ImageView clock = findViewById(R.id.clockImageView);
//        clock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), CompleteLessonActivity.class);
//                startActivity(intent);
//            }
//        });
    }


    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                updateTimerText(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // Handle what happens when the timer finishes

            }
        }.start();
    }

    private void updateTimerText(long millisUntilFinished) {
        // Format the remaining time as mm:ss
        long seconds = millisUntilFinished / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);

        // Update the timer TextView
        timerTextView.setText(timeFormatted);
    }
}
