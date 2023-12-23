package com.example.a4lingo.Controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.adapter.MistakeAdapter;
import com.example.a4lingo.item.MistakeItem;

import java.util.ArrayList;
import java.util.List;

public class MistakeActivity extends MainActivity {
    @Override
    protected void renderLayout(){
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_mistake, root, false);

        renderAnInstance(v);

        root.addView(v);
    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();

    }

    private void renderAnInstance(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewMistakes);
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
