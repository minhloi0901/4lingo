package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.RegisterService;

public class RegisterActivity extends OneTopNavActivity {
    private RegisterService registerService = new RegisterService();
    private boolean isHidden = true;
    private boolean isHidden1 = true;
    private String userName;
    private String email;
    private String password1;
    private String password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Đăng ký", "");
    }

    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_register, root, false);

        root.addView(v);
    }
    protected void renderNavigation() {
        super.renderNavigation();

        EditText userNameEditText = findViewById(R.id.loginName);
        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                userName = editable.toString();
            }
        });

        EditText emailEditText = findViewById(R.id.email);
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email = editable.toString();
            }
        });

        EditText passwordText1 = findViewById(R.id.password);
        passwordText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password1 = editable.toString();
            }
        });
        EditText passwordText2 = findViewById(R.id.refillPassword);
        passwordText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password2 = editable.toString();
                if (!password1.equals(password2)){
                    passwordText2.setError("Passwords do not match");
                }else {
                    passwordText2.setError(null);
                }
            }
        });

        ImageView visible1 = findViewById(R.id.visibleButton1);
        visible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHidden) {
                    passwordText1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visible1.setImageResource(R.drawable.ic_view);
                } else {
                    passwordText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visible1.setImageResource(R.drawable.ic_hide);
                }

                isHidden = !isHidden;
                passwordText1.setSelection(passwordText1.getText().length());
            }
        });

        EditText refillPasswordText = findViewById(R.id.refillPassword);
        ImageView visible2 = findViewById(R.id.visibleButton2);
        visible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHidden1) {
                    refillPasswordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visible2.setImageResource(R.drawable.ic_view);
                } else {
                    refillPasswordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visible2.setImageResource(R.drawable.ic_hide);
                }

                isHidden1 = !isHidden1;
                refillPasswordText.setSelection(refillPasswordText.getText().length());
            }
        });

        TextView createAccount = findViewById(R.id.createAccountButton);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check
                int success = registerService.registerUser(userName, email, password1);

                if (success == 0){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
