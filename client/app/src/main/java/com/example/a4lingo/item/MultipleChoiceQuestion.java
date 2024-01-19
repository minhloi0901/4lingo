package com.example.a4lingo.item;

import java.util.List;

public class MultipleChoiceQuestion {
    private final String questionText;
    private final List<String> choices;
    private final int correctChoiceIndex;
    private int score;

    public MultipleChoiceQuestion(String questionText, List<String> choices, int correctChoiceIndex, int score) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctChoiceIndex = correctChoiceIndex;
        this.score = score;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getCorrectChoiceIndex() {
        return correctChoiceIndex;
    }

    public int getScore() {
        return score;
    }
}
