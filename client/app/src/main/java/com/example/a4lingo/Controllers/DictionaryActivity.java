package com.example.a4lingo.Controllers;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.DictionaryService;
import com.example.a4lingo.adapter.DictionarySearchedWordsAdapter;

import java.util.Arrays;
import java.util.List;

public class DictionaryActivity extends MainActivity {
    private DictionarySearchedWordsAdapter adapter;
    private DictionaryService dictionaryService = new DictionaryService();
    @Override
    protected void renderLayout(){
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_dictionary, root, false);

        renderAnInstance(v);

        root.addView(v);
    }

    private void renderAnInstance(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.searchedWordsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<String> words = dictionaryService.getSuggestWords("UserID", "");

        adapter = new DictionarySearchedWordsAdapter(this, words);
        recyclerView.setAdapter(adapter);
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

                RecyclerView recyclerView = findViewById(R.id.searchedWordsRecyclerView);
                if (adapter.getItemCount() > 0){
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the adapter with the new list of matching words
                List<String> matchingWords = dictionaryService.getSuggestWords("UserID", charSequence.toString());
                adapter.updateSearchedWords(matchingWords);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        findViewById(R.id.cancelSearchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);

                RecyclerView recyclerView = findViewById(R.id.searchedWordsRecyclerView);
                recyclerView.setVisibility(View.GONE);

                editText.setText("");
            }
        });

    }
}
