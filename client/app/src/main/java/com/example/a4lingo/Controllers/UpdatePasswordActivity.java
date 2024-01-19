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
import com.example.a4lingo.Services.UpdatePasswordService;
import com.example.a4lingo.Services.Utils;

public class UpdatePasswordActivity extends OneTopNavActivity{
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
        View view = layoutInflater.inflate(R.layout.activity_update_password, root, false);

        root.addView(view);
    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();

        EditText oldPassword = findViewById(R.id.oldPassword);
        EditText newPassword1 = findViewById(R.id.newPassword1);
        EditText newPassword2 = findViewById(R.id.newPassword2);

        TextView updatePasswordBtn = findViewById(R.id.updatePasswordBtn);
        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old = oldPassword.getText().toString();
                String new1 = newPassword1.getText().toString();
                String new2 = newPassword2.getText().toString();

                if (old.equals("")){
                    oldPassword.setError("Empty old password!");
                    return;
                }

                if (new1.equals("")){
                    newPassword1.setError("Empty new password!");
                    return;
                }

                if (new1.equals(old)){
                    newPassword1.setError("The new password must be different from the old password!");
                    return;
                }

                if (!new2.equals(new1)){
                    newPassword2.setError("New password's not match!");
                    return;
                }

                UpdatePasswordService updatePasswordService = new UpdatePasswordService(getApplicationContext());
                String token = Utils.getToken(getApplicationContext());

                if (token != null){
                    updatePasswordService.updatePassword(token, old, new1, new Utils.Callback() {
                        @Override
                        public void onSuccess(String response) {
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
