package com.example.a4lingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class WordDictionaryActivity extends DictionaryActivity{
    @Override
    protected void renderLayout(){
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        root.removeAllViews();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_word_dictionary, root, false);

        // Retrieve data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String word = intent.getStringExtra("WORD");

            if (word == null){
                return;
            }

            ImageView imageView = v.findViewById(R.id.cancelSearchButton);
            imageView.setVisibility(View.VISIBLE);

            EditText editText = v.findViewById(R.id.searchWordEditText);
            editText.setText(word);

            TextView spellingPronunciation = v.findViewById(R.id.spellingPronunciationTextView);
            spellingPronunciation.setText("/ˈlɪŋɡəʊ/");

            // Set list of suggest words for WORD in ScrollView
            renderAnInstance(v, "1. ngoại ngữ", "\u2022" + " If you live abroad it helps to know the local lingo.", "Nếu bạn sống ở nước ngoài, việc biết biệt ngữ địa phương sẽ giúp ích.");
            renderAnInstance(v, "2. ngoại ngữ", "\u2022" + " If you live abroad it helps to know the local lingo.", "Nếu bạn sống ở nước ngoài, việc biết biệt ngữ địa phương sẽ giúp ích.");

        }

        root.addView(v);
    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();
    }

    private void renderAnInstance(View v, String meaning, String engSentence, String vieSentence) {
        LinearLayout rootLayout = v.findViewById(R.id.wordMeaningLinearLayout);

        // Inflate the dictionary word item layout
        LayoutInflater inflater = getLayoutInflater();
        View dictionaryItem = inflater.inflate(R.layout.dictionary_word_item, rootLayout, false);

        // Find views in the inflated layout
        TextView vieMeaning = dictionaryItem.findViewById(R.id.vieMeaning);
        TextView engSen = dictionaryItem.findViewById(R.id.engSentence);
        TextView vieSen = dictionaryItem.findViewById(R.id.vieSentence);

        vieMeaning.setText(meaning);
        engSen.setText(engSentence);
        vieSen.setText(vieSentence);

        // Add the inflated view to the root layout
        rootLayout.addView(dictionaryItem);
    }
}
