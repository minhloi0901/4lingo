package com.example.a4lingo.adapter;

import com.example.a4lingo.item.ContestItem;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommingContestAdapter extends ContestItemAdapter {

    public CommingContestAdapter(List<ContestItem> contestItemList) {
        super(contestItemList);
    }

    @Override
    protected String getRemainingTime(ContestItem item) {
        int duration = item.getDuration(); // Duration in minutes
        long hours = TimeUnit.MINUTES.toHours(duration);
        long minutes = TimeUnit.MINUTES.toMinutes(duration) - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%dh %02dm", hours, minutes);
    }
}
