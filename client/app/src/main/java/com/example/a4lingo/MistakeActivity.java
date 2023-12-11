package com.example.a4lingo;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MistakeActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistake);

        renderLayout();
        renderNavigation();

        renderAnInstance();
    }

    private void renderAnInstance() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewMistakes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<MistakeItem> mistakeList = new ArrayList<>();
        // Add Mistake objects to the list
        mistakeList.add(new MistakeItem("Type 1", "Mistake description 1"));
        mistakeList.add(new MistakeItem("Type 2", "Mistake description 2"));
        mistakeList.add(new MistakeItem("Type 3", "Mistake description 3"));


        MistakeAdapter adapter = new MistakeAdapter(mistakeList, this);
        recyclerView.setAdapter(adapter);
    }
}
