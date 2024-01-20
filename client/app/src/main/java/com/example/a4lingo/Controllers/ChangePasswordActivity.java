package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.ChangePasswordService;

public class ChangePasswordActivity extends OneTopNavActivity {
    private String theEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renderLayout("Đổi mật khẩu", null);
    }

    @Override
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.activity_change_password, root, false);
        root.addView(view);

        getIntentData();
        renderEmail();
        renderNavigation();
    }

    private void renderEmail() {
        TextView emailTextView = findViewById(R.id.staticEmail);
        if (theEmail != null && !theEmail.isEmpty()) {
            emailTextView.setText(theEmail);
        } else {
            emailTextView.setText("No email provided");
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("email")) {
            theEmail = intent.getStringExtra("email");
        }
    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();

        EditText pass1 = findViewById(R.id.changePassword1);
        EditText pass2 = findViewById(R.id.changePassword2);

        String password1 = String.valueOf(pass1.getText().toString());
        String password2 = String.valueOf(pass2.getText().toString());

        TextView changeBtn = findViewById(R.id.ChangePass);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = pass1.getText().toString().trim();
                String password2 = pass2.getText().toString().trim();

                if (!password1.equals(password2)) {
                    Toast.makeText(ChangePasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    ChangePasswordService changePasswordService = new ChangePasswordService(ChangePasswordActivity.this);
                    changePasswordService.changePass(theEmail, password1);
                }
            }
        });
    }
}
