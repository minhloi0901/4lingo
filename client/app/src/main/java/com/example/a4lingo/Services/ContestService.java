package com.example.a4lingo.Services;

import com.example.a4lingo.item.ContestItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ContestService {
    public ContestItem getContest(int contest_id) {
        return new ContestItem(1, 101, 50, "Contest A", 3, new Date(), addHoursToCurrentTime(1), 60);
    }

    public ArrayList<Integer> getCurrentContest() {
        ArrayList<Integer> currentContest = new ArrayList<>();
        currentContest.add(1);
        currentContest.add(2);
        currentContest.add(3);
        currentContest.add(4);
        return currentContest;
    }

    public ArrayList<Integer> getPreviousContest() {
        ArrayList<Integer> previousContest = new ArrayList<>();
        previousContest.add(1);
        previousContest.add(2);
        previousContest.add(3);
        previousContest.add(4);
        return previousContest;
    }

    public ArrayList<Integer> getCommingContest() {
        ArrayList<Integer> commingContest = new ArrayList<>();
        commingContest.add(1);
        commingContest.add(2);
        commingContest.add(3);
        commingContest.add(4);
        return commingContest;
    }

    private static Date addHoursToCurrentTime(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
