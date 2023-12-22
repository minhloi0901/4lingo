package com.example.a4lingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class LearningPathActivity extends OneTopNavActivity {
    private Button button1, button2, button3, button4, continueButton;
    private Button lastSelectedButton = null;
    private int questionId = 0;

    // Sample questions
    private ArrayList<String> questions = null;
    // Sample answers
    private String[][] answers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renderLayout("Xác định lộ trình", null);
        renderNavigation();
    }
    @Override
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_learning_path, root, false);
        createSampleData();
        getIntentData();
        renderAnInstance(v);
        root.addView(v);
    }

    private void createSampleData() {
        // Sample question ID
        questionId = 0;

        // Sample questionss
        questions = new ArrayList<>();
        questions.add("What is the capital of France?");
        questions.add("Who wrote 'Hamlet'?");
        questions.add("What is the formula for water?");

        // Sample answers
        answers = new String[][]{
                {"Paris", "Rome", "Berlin", "Madrid"},   // Options for the first question
                {"William Shakespeare", "Charles Dickens", "Jane Austen", "Mark Twain"}, // Options for the second question
                {"H2O", "CO2", "O2", "N2"}               // Options for the third question
        };
    }

    private void renderAnInstance(View v) {
        Button btn1 = v.findViewById(R.id.button1);
        Button btn2 = v.findViewById(R.id.button2);
        Button btn3 = v.findViewById(R.id.button3);
        Button btn4 = v.findViewById(R.id.button4);
        TextView question = v.findViewById(R.id.talkingBoxTextView);

        String tmp = questions.get(questionId);

        question.setText(questions.get(questionId));
        btn1.setText(answers[questionId][0]);
        btn2.setText(answers[questionId][1]);
        btn3.setText(answers[questionId][2]);
        btn4.setText(answers[questionId][3]);
    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        continueButton = findViewById(R.id.continueButton);

        View.OnClickListener choiceButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastSelectedButton != null) {
                    lastSelectedButton.setBackgroundResource(R.drawable.choice_background);
                }

                lastSelectedButton = (Button) view;
                lastSelectedButton.setBackgroundResource(R.drawable.choice_background_selected);
            }
        };

        button1.setOnClickListener(choiceButtonClickListener);
        button2.setOnClickListener(choiceButtonClickListener);
        button3.setOnClickListener(choiceButtonClickListener);
        button4.setOnClickListener(choiceButtonClickListener);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastSelectedButton != null) {
                    int selectedButtonId = -1;
                    if (lastSelectedButton == button1) selectedButtonId = 0;
                    else if (lastSelectedButton == button2) selectedButtonId = 1;
                    else if (lastSelectedButton == button3) selectedButtonId = 2;
                    else if (lastSelectedButton == button4) selectedButtonId = 3;

                    // SEND SELECTED ANSWER (selectedButtonId) to server
                    // BEGIN

                    //END

                    if (questionId < questions.size() - 1) {
                        Intent intent = new Intent(LearningPathActivity.this, LearningPathActivity.class);
                        int newQuestionId = questionId + 1;
                        intent.putExtra("question_id", newQuestionId); // integer
                        intent.putStringArrayListExtra("question", questions); // ArrayList<String>
                        intent.putExtra("answer", answers); // String[][]
                        startActivity(intent);
                    }
                    else {
                        // HANDLE WHEN OUT OF QUESTION
                        // BEGIN
                        Toast.makeText(LearningPathActivity.this, "Out of question", Toast.LENGTH_SHORT).show();
                        // END
                    }
                }
                else {
                    Toast.makeText(LearningPathActivity.this, "Hãy chọn một câu trả lời ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();

        // Get the question ID
        if (intent.hasExtra("question_id")) {
            questionId = intent.getIntExtra("question_id", -1); // -1 as default value
        }

        // Get the list of questions
        if (intent.hasExtra("question")) {
            questions = intent.getStringArrayListExtra("question");
        }

        // Get the 2D array of answers
        if (intent.hasExtra("answer")) {
            // Assuming the 2D array is serialized as an array of strings
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Serializable serializedAnswers = bundle.getSerializable("answer");
                if (serializedAnswers instanceof String[][]) {
                    answers = (String[][]) serializedAnswers;
                }
            }
        }
    }
}
