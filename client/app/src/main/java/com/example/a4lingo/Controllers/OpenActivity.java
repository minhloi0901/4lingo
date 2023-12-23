package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a4lingo.R;

public class OpenActivity extends AppCompatActivity {
    private final String TAG = "OpenActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        Log.d(TAG, "onCreate: clicked");

        renderLayout();
        renderNavigation();
    }
    protected void renderLayout() {
        ImageView imageView = findViewById(R.id.open_bird);

        // Get the background color of the root layout or the desired color
        Drawable backgroundDrawable  = getResources().getDrawable(R.drawable.background1);
        int backgroundColor = Color.TRANSPARENT;
        if (backgroundDrawable instanceof ColorDrawable) {
            backgroundColor = ((ColorDrawable) backgroundDrawable).getColor();
        }

        imageView.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP);
    }

    protected void renderNavigation() {
        Log.d(TAG, "renderNavigation: clicked");
        TextView register = findViewById(R.id.open_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView login = findViewById(R.id.open_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
