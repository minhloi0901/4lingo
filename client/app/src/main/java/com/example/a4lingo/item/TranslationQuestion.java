package com.example.a4lingo.item;

import java.util.List;

public class TranslationQuestion {
    private final String originalSentence;
    private final String destinationSentence;
    private final List<String> possibleWords;

    public TranslationQuestion(String originalSentence, String destinationSentence, List<String> possibleWords) {
        this.originalSentence = originalSentence;
        this.destinationSentence = destinationSentence;
        this.possibleWords = possibleWords;
    }

    public String getOriginalSentence() {
        return originalSentence;
    }

    public String getDestinationSentence() {
        return destinationSentence;
    }

    public List<String> getPossibleWords() {
        return possibleWords;
    }
}
