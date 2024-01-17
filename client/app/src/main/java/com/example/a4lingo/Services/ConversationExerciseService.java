package com.example.a4lingo.Services;

public class ConversationExerciseService {
    public String[][] getCurrentConversationExercise(String userID) {
        return new String[][] {
                {"Hi! What can I do for you?"},
                {"Hi professor. I wanted to talk to you about my grade for the last assignment.", "Well I got  lower grade than I was expecting and was hoping to better understand what I did wrong."},
                {"Hi! What can I do for you?"},
                {"Choice A", "Choice B", "Choice C", "Choice D"}
        };
    }
}
