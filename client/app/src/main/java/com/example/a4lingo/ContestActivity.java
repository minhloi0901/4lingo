package com.example.a4lingo;

import android.os.Bundle;

public class ContestActivity extends OneTopNavActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Cuộc thi", "TẠO");
    }
    @Override
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);


    }
    @Override
    protected void renderNavigation() {
        super.renderNavigation();
    }
}
