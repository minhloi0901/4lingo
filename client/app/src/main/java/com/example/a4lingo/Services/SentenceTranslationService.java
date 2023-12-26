package com.example.a4lingo.Services;

import com.example.a4lingo.item.TranslationQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SentenceTranslationService {
    public List<TranslationQuestion> getTranslationQuestions() {
        List<TranslationQuestion> questions = new ArrayList<>();

        // Add translation questions to the list
        questions.add(new TranslationQuestion("I smile because you smile.", "Tôi cười vì bạn đã cười", Arrays.asList("Tôi", "cười", "vì", "bạn", "đã", "cười", "cho", "thấy", "Cô")));
        questions.add(new TranslationQuestion("The sun is shining.", "Mặt trời đang sáng", Arrays.asList("Mặt trời", "đang", "sáng")));
        questions.add(new TranslationQuestion("I love learning new languages.", "Tôi yêu học ngôn ngữ mới", Arrays.asList("Tôi", "yêu", "học", "ngôn ngữ", "mới")));
        questions.add(new TranslationQuestion("Have a great day!", "Chúc bạn một ngày tốt lành!", Arrays.asList("Chúc", "bạn", "một", "ngày", "tốt lành")));
        questions.add(new TranslationQuestion("Where is the nearest restaurant?", "Nhà hàng gần nhất ở đâu?", Arrays.asList("Nơi", "Nhà hàng", "gần nhất", "ở đâu?")));
        // Add more questions as needed

        return questions;
    }
}
