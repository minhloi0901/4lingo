package com.example.a4lingo.Services;

import com.example.a4lingo.item.MultipleChoiceQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleChoiceService {
    public List<MultipleChoiceQuestion> getMultipleChoiceQuestions() {
        List<MultipleChoiceQuestion> questions = new ArrayList<>();

        // Sample question 1
        List<String> choices1 = Arrays.asList("Choice A", "Choice B", "Choice C", "Choice D");
        MultipleChoiceQuestion question1 = new MultipleChoiceQuestion("What is the capital of France?", choices1, 2);
        questions.add(question1);

        // Sample question 2
        List<String> choices2 = Arrays.asList("Choice X", "Choice Y", "Choice Z");
        MultipleChoiceQuestion question2 = new MultipleChoiceQuestion("What is the largest planet in our solar system?", choices2, 0);
        questions.add(question2);

        // Add more questions as needed...

        return questions;
    }
}
