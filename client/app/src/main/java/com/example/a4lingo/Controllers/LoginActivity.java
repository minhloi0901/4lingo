package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.LogInService;
import com.example.a4lingo.Services.RegisterService;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends OneTopNavActivity {
    private final String TAG = "LoginActivity";
    EditText userNameText;
    EditText passwordText;
    private String userName;
    private String password;
    private boolean isPasswordVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Đăng nhập", "");
    }
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_login, root, false);

        root.addView(v);

        // Set underline for forget password
        String text = "Quên mật khẩu";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView getPassword = findViewById(R.id.login_forget_password);
        getPassword.setText(spannableString);


        userNameText = findViewById(R.id.login_name);
        passwordText = findViewById(R.id.login_password);

        // Set view password
        ImageView visibleButton = findViewById(R.id.login_visibleButton);
        visibleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide the password
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPasswordVisible = false;
                    visibleButton.setImageResource(R.drawable.ic_hide); // Change the icon to 'eye'
                } else {
                    // Show the password
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPasswordVisible = true;
                    visibleButton.setImageResource(R.drawable.ic_view); // Change the icon to 'eye-off'
                }

                // Move cursor to the end of the text to prevent it from being reset
                passwordText.setSelection(passwordText.getText().length());
            }
        });
    }

    protected void renderNavigation() {
        super.renderNavigation();

        // Navigate get password
        TextView getPasswordText = findViewById(R.id.login_forget_password);
        getPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetPasswordActivity.class);
                startActivity(intent);
            }
        });

        // Navigate login
        TextView loginButton = findViewById(R.id.login_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch username and password when the login button is clicked
                EditText userNameText = findViewById(R.id.login_name);
                String userName = userNameText.getText().toString().trim();

                EditText passwordText = findViewById(R.id.login_password);
                String password = passwordText.getText().toString().trim();

                // Validate username and password
                if (userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform login
                performLogin(userName, password);
            }
        });

        // Set login by phone number
        LinearLayout loginByPhone = findViewById(R.id.login_by_phone);
        loginByPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Set login by facebook
        LinearLayout loginByFB = findViewById(R.id.login_by_fb);
        loginByFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Set login by google
        LinearLayout loginByGG = findViewById(R.id.login_by_gg);
        loginByGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Navigate to register
        TextView registerView = findViewById(R.id.login_register);
        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void performLogin(String userName, String password) {
        LogInService logInService = new LogInService(LoginActivity.this);
        logInService.login(userName, password, new LogInService.LoginCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.optString("message", "");

                        if ("Login success!".equals(message)) {
                            // Navigate to HomeActivity if login is successful
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Handle other messages or errors
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    // Handle the error, e.g., show a toast
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
