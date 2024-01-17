package com.example.a4lingo.Services;

import java.util.Arrays;
import java.util.List;

public class DictionaryService {
    public List<String> getSuggestWords(String userID, String searchingText) {
        if (searchingText.equals("")){
            return Arrays.asList("Hello", "Duo", "Lingo");
        }else{
            return Arrays.asList("Hello", "Duo", "Searching");
        }
    }
}
