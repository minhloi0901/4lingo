package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.ConversationExerciseService;
import com.example.a4lingo.Services.Utils;

import java.util.Random;

public class ConversationExerciseActivity extends MainActivity{
    private final ConversationExerciseService conversationExerciseService = new ConversationExerciseService();
    private final String[][] messages = conversationExerciseService.getCurrentConversationExercise(null);
    private int currentMessageIndex = 0;
    private View v = null;
    private int correctCount = 0;
    private int incorrectCount = 0;
    private long startTimeMillis;
    @Override
    protected void renderLayout() {
        super.renderLayout();
        startTimeMillis = SystemClock.elapsedRealtime();

        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        v = layoutInflater.inflate(R.layout.activity_conversation_exercise, root, false);

        renderAnInstance(v);

        root.addView(v);
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
            }
        });
    }

    private void renderAnInstance(View v) {
        LinearLayout linearLayout = v.findViewById(R.id.messagesLayout);

        addNpcMessage(linearLayout);
    }

    private void renderResponsesForSelecting(LinearLayout linearLayout) {
        LinearLayout mulChoicesLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.vertical_multiple_choices_layout, linearLayout, false);

        // Create an instance of Random
        Random random = new Random();

        // Generate a random number within the specified range
        int randomNumber = random.nextInt(messages[currentMessageIndex].length);
        Utils.swap(messages[currentMessageIndex], 0, randomNumber);
        for (int i = 0; i < messages[currentMessageIndex].length; i++)
            mulChoicesLayout.addView(getAChoice(linearLayout, mulChoicesLayout, messages[currentMessageIndex][i], i == randomNumber));

        linearLayout.addView(mulChoicesLayout);

        // Set on click listener.
        TextView continueBtn = v.findViewById(R.id.continueButton);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Please select the correct response!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private TextView getAChoice(LinearLayout linearLayout, LinearLayout mulChoicesLayout, String text, boolean isCorrectAnswer) {
        // Use LayoutInflater to inflate the choice TextView from XML
        LayoutInflater inflater = LayoutInflater.from(linearLayout.getContext());
        TextView choice = (TextView) inflater.inflate(R.layout.choice_item, linearLayout, false);

        // Set attributes for the choice TextView
        choice.setText(text);
        choice.setTag(isCorrectAnswer);

        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Index", String.valueOf(currentMessageIndex));
                boolean isCorrect = (boolean) choice.getTag();
                if (isCorrect){
                    linearLayout.removeView(mulChoicesLayout);
                    addUserMessage(linearLayout, text);
                    correctCount++;
                    Log.d("Correct count", String.valueOf(correctCount));
                }
                else{
                    choice.setBackgroundResource(R.drawable.border_gray);
                    choice.setAlpha(0.3f);
                    choice.setOnClickListener(null);
                    incorrectCount++;
                    Log.d("Incorrect count", String.valueOf(incorrectCount));
                }
            }
        });

        return choice;
    }

    private void addUserMessage(LinearLayout linearLayout, String text) {
        LinearLayout userLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.conversation_user_item, linearLayout, false);
        ImageView audioBtn = userLayout.findViewById(R.id.audioButton);
        audioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start Audio
            }
        });

        TextView userMessageLayout = userLayout.findViewById(R.id.messageTextView);
        userMessageLayout.setText(indentText(text));
        linearLayout.addView(userLayout);
        currentMessageIndex++;

        TextView continueBtn = v.findViewById(R.id.continueButton);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCompletion())
                    return;
                addNpcMessage(linearLayout);
            }
        });

    }

    private void addNpcMessage(LinearLayout linearLayout) {
        LinearLayout npcLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.conversation_npc_item, linearLayout, false);

        // Set audio
        ImageView audioBtn = npcLayout.findViewById(R.id.audioButton);
        audioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start Audio
            }
        });

        // Set message
        TextView npcMessageLayout = npcLayout.findViewById(R.id.messageTextView);
        npcMessageLayout.setText(indentText(messages[currentMessageIndex][0]));
        currentMessageIndex++;

        // Add item
        linearLayout.addView(npcLayout);

        // Set responses
        TextView continueBtn = v.findViewById(R.id.continueButton);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCompletion())
                    return;
                renderResponsesForSelecting(linearLayout);
            }
        });
    }

    private boolean checkCompletion() {
        if (currentMessageIndex == messages.length){
            long endTimeMillis = SystemClock.elapsedRealtime();  // Record the end time
            long elapsedTimeSeconds = (endTimeMillis - startTimeMillis) / 1000;

            Intent intent = new Intent(getApplicationContext(), CompleteLessonActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("SCORE", 100);
            intent.putExtra("TIME", elapsedTimeSeconds);
            intent.putExtra("ACCURACY", correctCount  * 100.0f / (incorrectCount + correctCount));
            intent.putExtra("LESSON_ID", 001);
//            intent.putExtra("LESSON_ID", "001");

            startActivity(intent);
            finish();

            return true;
        }
        return false;
    }

    private String indentText(String text) {
        return "      " + text;
    }
}
