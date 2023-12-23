package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a4lingo.R;

public class RegisterActivity extends OneTopNavActivity {
    private boolean isHidden = true;
    private boolean isHidden1 = true;
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

        EditText passwordText = findViewById(R.id.password);
        ImageView visible1 = findViewById(R.id.visibleButton1);
        visible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHidden) {
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visible1.setImageResource(R.drawable.ic_view);
                } else {
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visible1.setImageResource(R.drawable.ic_hide);
                }

                isHidden = !isHidden;
                passwordText.setSelection(passwordText.getText().length());
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
    }
    protected void renderNavigation() {
        super.renderNavigation();
        TextView createAccount = findViewById(R.id.createAccountButton);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check


                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                intent.putExtra("USER_NAME", userName);
                startActivity(intent);
            }
        });

    }
}
