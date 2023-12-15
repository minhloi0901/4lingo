package com.example.a4lingo;

import android.os.Bundle;

public class CommunicationActivity extends OneTopNavActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Cộng đồng", null);
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
