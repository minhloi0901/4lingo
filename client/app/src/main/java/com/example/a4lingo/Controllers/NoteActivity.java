package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.NoteService;
import com.example.a4lingo.adapter.NoteAdapter;
import com.example.a4lingo.item.WordItem;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends MainActivity{
    int user_id = 0;
    @Override
    protected void renderLayout(){
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_note, root, false);

        getIntentData();
        renderAnInstance(v);
        root.addView(v);
    }

    private void getIntentData() {
        // Get the intent that started this activity
        Intent intent = getIntent();

        // Check if the intent has extra data with the tag USER_ID
        if (intent.hasExtra("USER_ID")) {
            // Retrieve the USER_ID value and store it in user_id
            user_id = intent.getIntExtra("USER_ID", 0); // 0 is a default value
        } else {
            // Handle the case where USER_ID is not provided
            // For example, you might set a default value or show an error message
            user_id = 0; // Setting default value as 0
            // Optionally, you can show an error message or take other appropriate actions
        }
    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();
    }

    private void renderAnInstance(View v) {
        // Create a list of WordItems
        List<WordItem> wordItemList = new ArrayList<>();
//        wordItemList.add(new WordItem("lingo", "(danh từ)" + " " + "biệt ngữ"));
//        wordItemList.add(new WordItem("conversation", "(danh từ)" + " " + "hội thoại"));

        NoteService noteService = new NoteService();
        wordItemList = noteService.getListWord(user_id);

        // Find the RecyclerView
        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewNotes);

        // Create and set the adapter
        NoteAdapter wordAdapter = new NoteAdapter(this, wordItemList);
        recyclerView.setAdapter(wordAdapter);

        // Set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
