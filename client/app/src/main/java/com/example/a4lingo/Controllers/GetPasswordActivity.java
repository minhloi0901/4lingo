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
import com.example.a4lingo.Services.GetPasswordService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetPasswordActivity extends OneTopNavActivity {
    private Date expirationDate;
    private String token;
    private String theEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renderLayout("Lấy lại tài khoản", "");
    }

    protected void renderLayout(String pageTitle, String rightButtonText) {
        super.renderLayout(pageTitle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.activity_get_password, root, false);
        root.addView(view);

        setupSendEmailButton();
        setupVerifyButton();
    }

    private void setupVerifyButton() {
        final EditText tokenText = findViewById(R.id.TokenText);
        TextView verifyButton = findViewById(R.id.verifyTextVeiw);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userToken = tokenText.getText().toString().trim();
                Date currentDate = new Date();

                if (!userToken.equals(token)) {
                    Toast.makeText(GetPasswordActivity.this, "Token does not match.", Toast.LENGTH_SHORT).show();
                } else if (expirationDate == null) {
                    Toast.makeText(GetPasswordActivity.this, "Expiration date is not set.", Toast.LENGTH_SHORT).show();
                } else if (currentDate.after(expirationDate)) {
                    Toast.makeText(GetPasswordActivity.this, "Token has expired.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(GetPasswordActivity.this, ChangePasswordActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("email", theEmail);
                    startActivity(intent);
                }
            }
        });
    }

    private void setupSendEmailButton() {
        final EditText emailEditText = findViewById(R.id.email);
        TextView sendEmailButton = findViewById(R.id.verificationCode);
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                theEmail = email;
                if (!email.isEmpty()) {
                    sendPasswordResetEmail(email);
                } else {
                    Toast.makeText(GetPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendPasswordResetEmail(String email) {
        GetPasswordService getPasswordService = new GetPasswordService(GetPasswordActivity.this);
        getPasswordService.sendEmail(email, new GetPasswordService.EmailResponseCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String expiration = jsonResponse.optString("expiration", null);
                        token = jsonResponse.optString("token", null);
                        String message = jsonResponse.optString("message", "???");

                        if (expiration != null) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                            try {
                                expirationDate = dateFormat.parse(expiration);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(GetPasswordActivity.this, "Failed to parse date", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Toast.makeText(GetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(GetPasswordActivity.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> Toast.makeText(GetPasswordActivity.this, "Failed to send email: " + error, Toast.LENGTH_SHORT).show());
            }
        });
    }
}
