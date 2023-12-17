package com.example.a4lingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegisterActivity extends OneTopNavActivity {
    private boolean isHidden = true;
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
        ImageView visible1 = findViewById(R.id.visibleButton1);
        visible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHidden) {
                    visible1.setImageResource(R.drawable.ic_view);
                } else {
                    visible1.setImageResource(R.drawable.ic_hide);
                }

                isHidden = !isHidden;
            }
        });

        ImageView visible2 = findViewById(R.id.visibleButton2);
        visible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHidden) {
                    visible2.setImageResource(R.drawable.ic_view);
                } else {
                    visible2.setImageResource(R.drawable.ic_hide);
                }

                isHidden = !isHidden;
            }
        });

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
