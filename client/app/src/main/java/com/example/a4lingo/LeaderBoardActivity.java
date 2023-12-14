package com.example.a4lingo;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        renderLayout();
        renderNavigation();

        renderAnInstance();
    }

    private void renderAnInstance() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<RankingItem> rankingItemList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            rankingItemList.add(new RankingItem(i, R.drawable.profile_icon, "User " + i, 1000 + i * 100));
        }

        RankingAdapter adapter = new RankingAdapter(rankingItemList, this);
        recyclerView.setAdapter(adapter);
    }
}
