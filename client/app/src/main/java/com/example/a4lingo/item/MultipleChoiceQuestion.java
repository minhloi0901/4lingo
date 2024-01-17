package com.example.a4lingo.item;

import java.util.List;

public class MultipleChoiceQuestion {
    private final String questionText;
    private final List<String> choices;
    private final int correctChoiceIndex;

    public MultipleChoiceQuestion(String questionText, List<String> choices, int correctChoiceIndex) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctChoiceIndex = correctChoiceIndex;
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
}
