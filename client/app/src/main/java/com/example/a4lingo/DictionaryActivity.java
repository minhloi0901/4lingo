package com.example.a4lingo;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DictionaryActivity extends MainActivity {
    @Override
    protected void renderLayout(){
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_dictionary, root, false);

        // Example usage
        renderAnInstance(v, "Hello");
        renderAnInstance(v, "World");
        renderAnInstance(v, "Android");

        root.addView(v);
    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();

        EditText editText = findViewById(R.id.searchWordEditText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = findViewById(R.id.cancelSearchButton);
                imageView.setVisibility(View.VISIBLE);

                ScrollView scrollView = findViewById(R.id.searchedWordsScrollView);
                LinearLayout linearLayout = findViewById(R.id.searchedWordsLinearLayout);
                if (linearLayout.getChildCount() > 0){
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        findViewById(R.id.cancelSearchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);

                ScrollView scrollView = findViewById(R.id.searchedWordsScrollView);
                scrollView.setVisibility(View.GONE);

                editText.setText("");

            }
        });

    }



    private void renderAnInstance(View v, String word) {
        ScrollView scrollView = v.findViewById(R.id.searchedWordsScrollView);
        LinearLayout linearLayout = v.findViewById(R.id.searchedWordsLinearLayout);

        LayoutInflater inflater = getLayoutInflater();
        View dictionaryItem = inflater.inflate(R.layout.dictionary_item, linearLayout, false);

        TextView textView = dictionaryItem.findViewById(R.id.searchedWord);
        textView.setText(word);

        ImageView imageView = dictionaryItem.findViewById(R.id.forwardToWordPageButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WordDictionaryActivity.class);
                intent.putExtra("WORD", word);
                startActivity(intent);
            }
        });

        linearLayout.addView(dictionaryItem);
//        scrollView.setVisibility(View.VISIBLE);
    }
}
