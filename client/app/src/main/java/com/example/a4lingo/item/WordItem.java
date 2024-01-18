package com.example.a4lingo.item;

public class WordItem {
    private String word;
    private String pronunciation;
    private String audio_url;
    private String meaning;
    private String engSentence;
    private String vieSentence;
    public WordItem(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
    public WordItem(String word, String meaning, String pronunciation, String audio_url, String engSentence, String vieSentence){
        this.word = word;
        this.meaning = meaning;
        this.pronunciation = pronunciation;
        this.audio_url = audio_url;
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
    public String getPronunciation() {return pronunciation;}
    public String getAudio_url() {
        return audio_url;
    }
}
