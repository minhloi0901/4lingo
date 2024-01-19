package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.AchivementService;
import com.example.a4lingo.adapter.AchievementAdapter;
import com.example.a4lingo.item.AchievementItem;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends OneTopNavActivity{

    int user_id = 0;

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

    private void renderAnInstance(View v) {
        List<AchievementItem> achievementList = new ArrayList<>();
//        achievementList.add(new AchievementItem("Chúa tể nhiệm vụ", "Hoàn thành 10 nhiệm vụ", 5, 10, R.drawable.ic_achievement_instance));
//        achievementList.add(new AchievementItem("Thợ săn đêm", "Hoàn thành 3 bài học sau 10 giờ tối", 1, 3, R.drawable.ic_achievement_instance));
        AchivementService achivementService = new AchivementService();
        achievementList = achivementService.getAchivements(user_id);

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
