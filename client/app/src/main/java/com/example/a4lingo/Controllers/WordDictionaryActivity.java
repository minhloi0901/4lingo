package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.DictionaryService;
import com.example.a4lingo.Services.WordDictionaryService;
import com.example.a4lingo.adapter.DictionarySearchedWordsAdapter;
import com.example.a4lingo.item.WordItem;

import java.util.List;

public class WordDictionaryActivity extends DictionaryActivity{
    private WordDictionaryService wordDictionaryService = new WordDictionaryService();
    private DictionaryService dictionaryService = new DictionaryService();
    private DictionarySearchedWordsAdapter adapter;

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
            spellingPronunciation.setText(wordDictionaryService.getSpellingPronunciation(word));

            // Suggest words
            RecyclerView recyclerView = v.findViewById(R.id.searchedWordsRecyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            List<String> words = dictionaryService.getSuggestWords("UserID", word);
            adapter = new DictionarySearchedWordsAdapter(this, words);
            recyclerView.setAdapter(adapter);


            // Present meanings and examples
            List<WordItem> wordItemList = wordDictionaryService.getWordItemList(word);

            // Set list of suggest words for WORD in ScrollView
            for (WordItem wordItem: wordItemList) {
                renderAnInstance(v, wordItem);
            }
        }

        root.addView(v);
    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();

        ImageView audio = findViewById(R.id.audioButton);
        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sound
            }
        });
    }

    private void renderAnInstance(View v, WordItem wordItem) {
        LinearLayout rootLayout = v.findViewById(R.id.wordMeaningLinearLayout);

        // Inflate the dictionary word item layout
        LayoutInflater inflater = getLayoutInflater();
        View dictionaryItem = inflater.inflate(R.layout.dictionary_word_item, rootLayout, false);

        // Find views in the inflated layout
        TextView vieMeaning = dictionaryItem.findViewById(R.id.vieMeaning);
        TextView engSen = dictionaryItem.findViewById(R.id.engSentence);
        TextView vieSen = dictionaryItem.findViewById(R.id.vieSentence);

        vieMeaning.setText(wordItem.getMeaning());
        engSen.setText(wordItem.getEngSentence());
        vieSen.setText(wordItem.getVieSentence());

        // Add the inflated view to the root layout
        rootLayout.addView(dictionaryItem);
    }
}
