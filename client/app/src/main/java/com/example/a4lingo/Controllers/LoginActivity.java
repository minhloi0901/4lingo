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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lingo.R;

public class LoginActivity extends OneTopNavActivity {
    private final String TAG = "LoginActivity";
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

        // Get username
        EditText userNameText = findViewById(R.id.login_name);
        userName = userNameText.getText().toString();

        // Get password
        EditText passwordText = findViewById(R.id.login_password);
        password = passwordText.getText().toString();

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
                // check userName and password
                boolean user_check = check_user_information(userName, password);
                if (user_check) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
                            toast.cancel();
                        }
                    }, 3000);
                }
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

    private boolean check_user_information(String userName, String password) {
        // return true if success
        return true;
    }
}
