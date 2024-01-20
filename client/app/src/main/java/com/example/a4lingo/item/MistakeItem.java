package com.example.a4lingo.item;

public class MistakeItem {
    int mistake_id;
    private String assignmentType;
    private String mistakeDescription;

    public MistakeItem(int mistake_id, String assignmentType, String mistakeDescription) {
        this.mistake_id = mistake_id;
        this.assignmentType = assignmentType;
        this.mistakeDescription = mistakeDescription;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public String getMistakeDescription() {
        return mistakeDescription;
    }
}
