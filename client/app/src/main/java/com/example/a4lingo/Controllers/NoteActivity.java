package com.example.a4lingo.Controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.adapter.NoteAdapter;
import com.example.a4lingo.item.WordItem;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends MainActivity{
    @Override
    protected void renderLayout(){
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_note, root, false);

        renderAnInstance(v);

        root.addView(v);
    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();
    }

    private void renderAnInstance(View v) {
        // Create a list of WordItems
        List<WordItem> wordItemList = new ArrayList<>();
        wordItemList.add(new WordItem("lingo", "(danh từ)" + " " + "biệt ngữ"));
        wordItemList.add(new WordItem("conversation", "(danh từ)" + " " + "hội thoại"));

        // Find the RecyclerView
        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewNotes);

        // Create and set the adapter
        NoteAdapter wordAdapter = new NoteAdapter(this, wordItemList);
        recyclerView.setAdapter(wordAdapter);

        // Set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
