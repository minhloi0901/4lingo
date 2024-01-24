package com.example.a4lingo.Controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.LeaderBoardService;
import com.example.a4lingo.Services.Utils;
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

        final List<RankingItem>[] rankingItemList = new List[]{new ArrayList<>()};

        LeaderBoardService leaderBoardService = new LeaderBoardService(LeaderBoardActivity.this);
        leaderBoardService.get_all_users(new Utils.Callback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(LeaderBoardActivity.this, response, Toast.LENGTH_SHORT).show();
                        rankingItemList[0] = leaderBoardService.parseUserList(response);
                        RankingAdapter adapter = new RankingAdapter(rankingItemList[0], getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(LeaderBoardActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                // Alternatively, you can log the error for debugging purposes
                // Log.e("LeaderBoardActivity", "Network request failed: " + error);
            }
        });
    }
}
