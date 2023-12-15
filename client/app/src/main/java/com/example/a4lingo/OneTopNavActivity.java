package com.example.a4lingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OneTopNavActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_top_nav);

        renderLayout("", "");
        renderNavigation();
    }

    protected void renderLayout(String pageTittle, String rightButtonText) {
        TextView pageTittleTextView = findViewById(R.id.pageTittle);
        pageTittleTextView.setText(pageTittle);

        if (rightButtonText != null){
            TextView rightButtonTextView = findViewById(R.id.rightButton);
            rightButtonTextView.setText(rightButtonText);
        }
    }
    protected void renderNavigation() {
        ImageView goBackButton = findViewById(R.id.leftButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
