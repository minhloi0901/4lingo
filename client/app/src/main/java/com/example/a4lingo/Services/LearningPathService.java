package com.example.a4lingo.Services;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class LearningPathService {
    public ArrayList<String> getLearningPathQuestions() {
        ArrayList<String> questions = null;
        questions = new ArrayList<>();
        questions.add("What is the capital of France?");
        questions.add("Who wrote 'Hamlet'?");
        questions.add("What is the formula for water?");

        return questions;
    }

    public String[][] getLearningPathAnswers() {
        String[][] answers = new String[][]{
                {"Paris", "Rome", "Berlin", "Madrid"},   // Options for the first question
                {"William Shakespeare", "Charles Dickens", "Jane Austen", "Mark Twain"}, // Options for the second question
                {"H2O", "CO2", "O2", "N2"}               // Options for the third question
        };

        return  answers;
    }

    public String getLearningPath(ArrayList<Integer> selected_answer) {

        return "haloalo";
    }
}
