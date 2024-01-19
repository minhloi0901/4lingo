package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.RegisterService;
import com.example.a4lingo.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends OneTopNavActivity {
    private boolean isHidden = true;
    private boolean isHidden1 = true;
    private String userName;
    private String email;
    private String password1;
    private String password2;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Đăng ký", "");
    }

    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);

        ImageView leftBtn = findViewById(R.id.leftButton);

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
                userName = userNameEditText.getText().toString().trim();
                email = emailEditText.getText().toString().trim();
                password1 = passwordText1.getText().toString().trim();
                password2 = passwordText2.getText().toString().trim();
                EditText phoneNumberText = findViewById(R.id.phoneNumber);
                phoneNumber = phoneNumberText.getText().toString().trim();

                // Validate empty fields
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2) || TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate matching passwords
                if (!password1.equals(password2)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // CALL API HERE, AND TOAST THE RESPONSE
                // BEGIN
                RegisterService registerService = new RegisterService(RegisterActivity.this);
                registerService.registerUser(userName, password1, phoneNumber, email);
                // END

//                if (success == 0){
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
            }
        });
    }
}
