package com.example.a4lingo.Services;

import com.example.a4lingo.item.WordItem;

import java.util.ArrayList;
import java.util.List;

public class WordDictionaryService {
    public String getSpellingPronunciation(String word) {
        return "/ˈlɪŋɡəʊ/";
    }

    public List<WordItem> getWordItemList(String word) {
        List<WordItem> wordItemList = new ArrayList<>();
        wordItemList.add(new WordItem(word, "1. ngoại ngữ", "\u2022" + " If you live abroad it helps to know the local lingo.", "Nếu bạn sống ở nước ngoài, việc biết biệt ngữ địa phương sẽ giúp ích."));
        wordItemList.add(new WordItem(word, "2. ngoại ngữ", "\u2022" + " If you live abroad it helps to know the local lingo.", "Nếu bạn sống ở nước ngoài, việc biết biệt ngữ địa phương sẽ giúp ích."));
        return wordItemList;
    }
}
