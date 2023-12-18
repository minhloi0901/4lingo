package com.example.a4lingo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class LoginActivity extends OpenActivity {
    protected void renderLayout() {
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_login, root, false);

        root.addView(v);
    }

    protected void renderNavigation() {
        super.renderNavigation();
    }
}
