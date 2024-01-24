package com.example.a4lingo.item;

import java.util.List;

public class TranslationQuestion {
    private final String originalSentence;
    private final String destinationSentence;
    private final List<String> possibleWords;
    private final int level;
    private int score;

    public TranslationQuestion(String originalSentence, String destinationSentence, List<String> possibleWords, int score, int level) {
        this.originalSentence = originalSentence;
        this.destinationSentence = destinationSentence;
        this.possibleWords = possibleWords;
        this.score = score;
        this.level = level;
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

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }
}
