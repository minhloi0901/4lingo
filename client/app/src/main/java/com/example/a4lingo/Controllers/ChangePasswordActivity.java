package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.LearningPathService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ChangePasswordActivity extends OneTopNavActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renderLayout("Đổi mật khẩu", null);
        renderNavigation();
    }
    @Override
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_change_password, root, false);
//        getIntentData();
//        renderAnInstance(v);
        root.addView(v);
    }

    private void renderAnInstance(View v) {

    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();
    }

    private void getIntentData() {

    }
}
