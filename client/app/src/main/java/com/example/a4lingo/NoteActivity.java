package com.example.a4lingo;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.adapter.NoteAdapter;
import com.example.a4lingo.item.WordItem;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends MainActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        renderLayout();
        renderNavigation();

        renderAnInstance();
    }

    private void renderAnInstance() {
        // Create a list of WordItems
        List<WordItem> wordItemList = new ArrayList<>();
        wordItemList.add(new WordItem("lingo", "(danh từ)" + " " + "biệt ngữ"));
        wordItemList.add(new WordItem("conversation", "(danh từ)" + " " + "hội thoại"));

        // Find the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotes);

        // Create and set the adapter
        NoteAdapter wordAdapter = new NoteAdapter(this, wordItemList);
        recyclerView.setAdapter(wordAdapter);

        // Set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
