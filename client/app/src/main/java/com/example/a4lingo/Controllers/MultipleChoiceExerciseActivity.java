package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.MultipleChoiceService;
import com.example.a4lingo.Services.Utils;
import com.example.a4lingo.item.MultipleChoiceQuestion;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class MultipleChoiceExerciseActivity extends MainActivity{
    private View v = null;
    private long startTimeMillis;
    private final MultipleChoiceService multipleChoiceService = new MultipleChoiceService();
    private LinearLayout mulChoiceLayout;
    private final List<MultipleChoiceQuestion> questions = multipleChoiceService.getMultipleChoiceQuestions(null);
    private int questionIndex = 0;
    private int correctCount = 0;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void renderLayout() {
        super.renderLayout();
        // Start timer
        startTimeMillis = SystemClock.elapsedRealtime();

        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        v = layoutInflater.inflate(R.layout.activity_multiple_choice_exercise, root, false);
        mulChoiceLayout = v.findViewById(R.id.vertical_mul_choices_layout);

        renderAnInstance(v);

        root.addView(v);
    }

    private void renderAnInstance(View v) {
        mulChoiceLayout.removeAllViews();

        TextView continueBtn = v.findViewById(R.id.continueButton);
        // Check if the current questionIndex is within the valid range
        if (questionIndex >= 0 && questionIndex < questions.size()) {
            // Get the current question
            MultipleChoiceQuestion currentQuestion = questions.get(questionIndex);

            // Set the question text in the layout
            TextView questionTextView = v.findViewById(R.id.questionSentenceTextView);
            questionTextView.setText(currentQuestion.getQuestionText());

            // Clear existing choices in the layout
            mulChoiceLayout.removeAllViews();

            // Add each choice to the layout
            for (int i = 0; i < currentQuestion.getChoices().size(); i++) {
                String choiceText = currentQuestion.getChoices().get(i);
                boolean isCorrect = (i == currentQuestion.getCorrectChoiceIndex());

                TextView choiceTextView = getAChoice(mulChoiceLayout, choiceText, isCorrect);
                choiceTextView.setOnClickListener(getChoiceOnClickListener(continueBtn, isCorrect));
                mulChoiceLayout.addView(choiceTextView);
            }

            // Assert that user must select a choice
            continueBtn.setText("KIỂM TRA");
            continueBtn.setAlpha(0.3f);
            continueBtn.setOnClickListener(null);
        };
    }

    private View.OnClickListener getChoiceOnClickListener(TextView continueBtn, boolean isCorrect) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear backgrounds of all choices
                clearChoiceBackgrounds(mulChoiceLayout);

                // Set background for the selected choice
                view.setBackgroundResource(R.drawable.border_green);

                continueBtn.setAlpha(1.0f);
                continueBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isCorrect) {
                            String message = "Tuyệt vời!";
                            bottomSheetDialog = Utils.showBottomSheet(MultipleChoiceExerciseActivity.this, true, message);
                            correctCount++;
                        } else {
                            String message = "Cố gắng hơn nữa nhé!";
                            bottomSheetDialog = Utils.showBottomSheet(MultipleChoiceExerciseActivity.this, false, message);
                        }

                        setClickToMoveToNextQuestion(bottomSheetDialog);
                    }
                });
            }
        };
    }

    private void clearChoiceBackgrounds(LinearLayout mulChoiceLayout) {
        for (int i = 0; i < mulChoiceLayout.getChildCount(); i++) {
            Log.i("Choice index: ", String.valueOf(i));
            View choiceView = mulChoiceLayout.getChildAt(i);
            choiceView.setBackgroundResource(R.drawable.border_gray);
        }
    }

    private void setClickToMoveToNextQuestion(BottomSheetDialog bottomSheetDialog) {
        TextView continueBtn =  bottomSheetDialog.findViewById(R.id.continueButton);
        assert continueBtn != null;
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionIndex++;
                boolean completed = Utils.checkCompletion(questionIndex, questions, startTimeMillis, MultipleChoiceExerciseActivity.this, correctCount);
                if (completed){
                    finish();
                }else {
                    if (bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    }
                    renderAnInstance(v);
                }
            }
        });
    }

    @NonNull
    private TextView getAChoice(LinearLayout linearLayout, String text, boolean isCorrectAnswer) {
        // Use LayoutInflater to inflate the choice TextView from XML
        LayoutInflater inflater = LayoutInflater.from(linearLayout.getContext());
        TextView choice = (TextView) inflater.inflate(R.layout.choice_item, linearLayout, false);

        // Set attributes for the choice TextView
        choice.setText(text);
        choice.setTag(isCorrectAnswer);

        return choice;
    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();

        ImageView ignoreBtn = findViewById(R.id.ignoreButton);
        ignoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
