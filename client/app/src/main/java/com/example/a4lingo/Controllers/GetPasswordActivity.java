package com.example.a4lingo.Controllers;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a4lingo.R;

public class GetPasswordActivity extends OneTopNavActivity {
    private boolean isHidden = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Lấy lại tài khoản", "");
    }

    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_get_password, root, false);

        root.addView(v);
    }
    protected void renderNavigation() {
        super.renderNavigation();

        TextView verification = findViewById(R.id.verificationCode);
        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send verification code
            }
        });


        TextView sendEmail = findViewById(R.id.sendEmailButton);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

    }
}
