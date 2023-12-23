package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.ContestService;
import com.example.a4lingo.adapter.CommingContestAdapter;
import com.example.a4lingo.adapter.ContestItemAdapter;
import com.example.a4lingo.adapter.PreviousContestAdapter;
import com.example.a4lingo.item.ContestItem;

import java.util.ArrayList;

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

        getData();
        renderAnInstance(v);
        root.addView(v);
    }

    private void getData() {
        currentContest =  new ArrayList<>();
        commingContest =  new ArrayList<>();
        previousContest =  new ArrayList<>();

        ContestService contestService = new ContestService();
        ArrayList<Integer> lstCur = contestService.getCurrentContest();
        ArrayList<Integer> lstPrev = contestService.getPreviousContest();
        ArrayList<Integer> lstCom = contestService.getCommingContest();

        for (int i = 0; i < lstCur.size(); ++i) {
            ContestItem contestItem = contestService.getContest(lstCur.get(i));
            currentContest.add(contestItem);
        }

        for (int i = 0; i < lstPrev.size(); ++i) {
            ContestItem contestItem = contestService.getContest(lstPrev.get(i));
            previousContest.add(contestItem);
        }

        for (int i = 0; i < lstCom.size(); ++i) {
            ContestItem contestItem = contestService.getContest(lstCom.get(i));
            commingContest.add(contestItem);
        }
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
