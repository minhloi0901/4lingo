package com.example.a4lingo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.adapter.RankingAdapter;
import com.example.a4lingo.item.RankingItem;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends MainActivity {
    @Override
    protected void renderLayout(){
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_ranking, root, false);

        renderAnInstance(v);

        root.addView(v);
    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();
    }

    private void renderAnInstance(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<RankingItem> rankingItemList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            rankingItemList.add(new RankingItem(i, R.drawable.profile_icon, "User " + i, 1000 + i * 100));
        }

        RankingAdapter adapter = new RankingAdapter(rankingItemList, this);
        recyclerView.setAdapter(adapter);
    }
}
