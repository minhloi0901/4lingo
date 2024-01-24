package com.example.a4lingo.Services;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class LearningPathService {
    public ArrayList<String> getLearningPathQuestions() {
        ArrayList<String> questions = new ArrayList<>();
        questions.add("What is the difference between 'there,' 'their,' and 'they're'?");
        questions.add("Explain the use of past perfect tense in a sentence.");
        questions.add("Define and provide examples of similes and metaphors.");

        return questions;
    }

    public String[][] getLearningPathAnswers() {
        String[][] answers = new String[][]{
                {"Indicate location", "Possessive form", "Contraction of 'they are'", "None of the above"},   // Options for the first question
                {"Describes an action completed before another past action", "Expresses a general truth", "Indicates a future action", "No such tense in English"}, // Options for the second question
                {"Figures of speech comparing two different things using 'like' or 'as'", "Comparisons without using 'like' or 'as'", "Literal language only", "None of the above"}               // Options for the third question
        };

        return answers;
    }

    public String getLearningPath(ArrayList<Integer> selected_answer) {

        return "haloalo";
    }
}
