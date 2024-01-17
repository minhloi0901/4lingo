package com.example.a4lingo.item;

public class WordItem {
    private String word;
    private String meaning;
    private String engSentence;
    private String vieSentence;
    public WordItem(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
    public WordItem(String word, String meaning, String engSentence, String vieSentence){
        this.word = word;
        this.meaning = meaning;
        this.engSentence = engSentence;
        this.vieSentence = vieSentence;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }
    public String getEngSentence() {return engSentence;}
    public String getVieSentence() {return vieSentence;}
}
