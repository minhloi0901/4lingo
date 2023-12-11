package com.example.a4lingo;

public class MistakeItem {
    private String assignmentType;
    private String mistakeDescription;

    public MistakeItem(String assignmentType, String mistakeDescription) {
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
