package com.example.a4lingo.Controllers;

import android.os.Bundle;

public class OverallActivity extends OneTopNavActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Tổng quan", null);
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