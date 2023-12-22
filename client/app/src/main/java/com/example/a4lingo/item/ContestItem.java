package com.example.a4lingo.item;

import java.util.Date;

public class ContestItem {
    private int contestId;
    private int creator;
    private int numberOfRegisters;
    private String contestName;
    private int difficulty;
    private Date timeCreated;
    private Date timeBegin;
    private int duration;

    // Constructor
    public ContestItem(int contestId, int creator, int numberOfRegisters, String contestName, int difficulty, Date timeCreated, Date timeBegin, int duration) {
        this.contestId = contestId;
        this.creator = creator;
        this.numberOfRegisters = numberOfRegisters;
        this.contestName = contestName;
        this.difficulty = difficulty;
        this.timeCreated = timeCreated;
        this.timeBegin = timeBegin;
        this.duration = duration;
    }

    // Getters and Setters
    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getNumberOfRegisters() {
        return numberOfRegisters;
    }

    public void setNumberOfRegisters(int numberOfRegisters) {
        this.numberOfRegisters = numberOfRegisters;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Date timeBegin) {
        this.timeBegin = timeBegin;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
