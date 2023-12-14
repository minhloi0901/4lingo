package com.example.a4lingo.item;

public class RankingItem {
    private int rank;
    private int avatarResId;
    private String name;
    private int score;

    public RankingItem(int rank, int avatarResId, String name, int score) {
        this.rank = rank;
        this.avatarResId = avatarResId;
        this.name = name;
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public int getAvatarResId() {
        return avatarResId;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}


