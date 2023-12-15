package com.example.a4lingo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.adapter.AchievementAdapter;
import com.example.a4lingo.item.AchievementItem;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends OneTopNavActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Thành tích", null);
    }
    @Override
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);

        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_achievement, root, false);

        renderAnInstance(v);

        root.addView(v);
    }

    private void renderAnInstance(View v) {
        List<AchievementItem> achievementList = new ArrayList<>();
        achievementList.add(new AchievementItem("Chúa tể nhiệm vụ", "Hoàn thành 10 nhiệm vụ", 5, 10, R.drawable.ic_achievement_instance));
        achievementList.add(new AchievementItem("Thợ săn đêm", "Hoàn thành 3 bài học sau 10 giờ tối", 1, 3, R.drawable.ic_achievement_instance));


        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewAchievement);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // Instantiate the adapter and set it to your RecyclerView
        AchievementAdapter adapter = new AchievementAdapter(this, achievementList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();
    }
}
