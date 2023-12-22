package com.example.a4lingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.adapter.CommingContestAdapter;
import com.example.a4lingo.adapter.ContestItemAdapter;
import com.example.a4lingo.adapter.PreviousContestAdapter;
import com.example.a4lingo.item.ContestItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ContestActivity extends OneTopNavActivity {
    ArrayList<ContestItem> currentContest = new ArrayList<>();
    ArrayList<ContestItem> commingContest = new ArrayList<>();
    ArrayList<ContestItem> previousContest = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Cuộc thi", "TẠO");
    }
    @Override
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_contest, root, false);

        createSampleData();
        renderAnInstance(v);
        root.addView(v);
    }

    public static List<ContestItem> getSampleContestItems() {
        List<ContestItem> sampleItems = new ArrayList<>();

        // Sample data creation
        sampleItems.add(new ContestItem(1, 101, 50, "Contest A", 3, new Date(), addHoursToCurrentTime(1), 60));
        sampleItems.add(new ContestItem(2, 102, 30, "Contest B", 2, new Date(), addHoursToCurrentTime(2), 120));
        sampleItems.add(new ContestItem(3, 103, 40, "Contest C", 4, new Date(), addHoursToCurrentTime(3), 90));
        // Add more items as needed

        return sampleItems;
    }

    private static Date addHoursToCurrentTime(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    private void createSampleData() {
        currentContest = (ArrayList<ContestItem>) getSampleContestItems();
        commingContest = (ArrayList<ContestItem>) getSampleContestItems();
        previousContest = (ArrayList<ContestItem>) getSampleContestItems();
    }

    private void renderAnInstance(View v) {
        ContestItemAdapter currenAdapter = new ContestItemAdapter(currentContest);
        RecyclerView currentRecycleView = v.findViewById(R.id.currentContest);
        currentRecycleView.setLayoutManager(new LinearLayoutManager(this));
        currentRecycleView.setAdapter(currenAdapter);

        CommingContestAdapter commingAdapter = new CommingContestAdapter(commingContest);
        RecyclerView commingRecycleView = v.findViewById(R.id.commingContest);
        commingRecycleView.setLayoutManager(new LinearLayoutManager(this));
        commingRecycleView.setAdapter(commingAdapter);

        PreviousContestAdapter previousAdapter = new PreviousContestAdapter(previousContest);
        RecyclerView previousRecycleView = v.findViewById(R.id.happenedContest);
        previousRecycleView.setLayoutManager(new LinearLayoutManager(this));
        previousRecycleView.setAdapter(previousAdapter);
    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();

        TextView createContest = findViewById(R.id.rightButton);
        createContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check authority

                // Move to createContestActivity
                Intent intent = new Intent(getApplicationContext(), CreateContestActivity.class);
                startActivity(intent);
            }
        });
    }
}
