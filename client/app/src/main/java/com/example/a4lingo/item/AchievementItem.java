package com.example.a4lingo.item;
public class AchievementItem {

    private String name;
    private String description;
    private int progress;
    private int total;
    private int imageResourceId; // Resource ID for the achievement image

    public AchievementItem(String name, String description, int progress, int total, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.progress = progress;
        this.total = total;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getProgress() {
        return progress;
    }

    public int getTotal() {
        return total;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
