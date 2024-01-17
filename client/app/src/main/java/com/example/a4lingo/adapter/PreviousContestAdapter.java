package com.example.a4lingo.adapter;

import com.example.a4lingo.item.ContestItem;

import java.util.List;

public class PreviousContestAdapter extends ContestItemAdapter {
    public PreviousContestAdapter(List<ContestItem> contestItemList) {
        super(contestItemList);
    }

    @Override
    protected String getRemainingTime(ContestItem item) {
        return "Số lượng người tham gia: " + String.valueOf(item.getNumberOfRegisters());
    }
}
