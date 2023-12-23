package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.a4lingo.R;

import java.util.ArrayList;
import java.util.Random;

public class ConversationExerciseActivity extends MainActivity{
    private String[][] messages = new String[][] {
            {"Hi! What can I do for you?"},
            {"Hi professor. I wanted to talk to you about my grade for the last assignment.", "Well I got  lower grade than I was expecting and was hoping to better understand what I did wrong."},
            {"Hi! What can I do for you?"},
            {"Choice A", "Choice B", "Choice C", "Choice D"}
    };
    private int currentMessageIndex = 0;
    private LinearLayout root = null;
    private View v = null;
    @Override
    protected void renderLayout() {
        super.renderLayout();
        root = (LinearLayout) findViewById(R.id.content);
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
        // Choice's layout parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(40, 20, 40, 20);

        // Create an instance of Random
        Random random = new Random();

        // Generate a random number within the specified range
        int randomNumber = random.nextInt(messages[currentMessageIndex].length);
        swap(messages[currentMessageIndex], 0, randomNumber);
        for (int i = 0; i < messages[currentMessageIndex].length; i++){
            boolean isCorrect = false;
            if (i == randomNumber)
                isCorrect = true;

            mulChoicesLayout.addView(getAChoice(linearLayout, mulChoicesLayout, layoutParams, messages[currentMessageIndex][i], isCorrect));
        }

        linearLayout.addView(mulChoicesLayout);
    }

    private static <T> void swap(T[] array, int index1, int index2) {
        if (index1 >= 0 && index1 < array.length && index2 >= 0 && index2 < array.length) {
            T temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        } else {
            // Handle invalid indices if needed
            System.out.println("Invalid indices");
        }
    }

    @NonNull
    private TextView getAChoice(LinearLayout linearLayout, LinearLayout mulChoicesLayout, LinearLayout.LayoutParams layoutParams, String text, boolean isCorrectAnswer) {
        TextView choice = new TextView(mulChoicesLayout.getContext());
        choice.setLayoutParams(layoutParams);
        choice.setBackgroundResource(R.drawable.border_gray);
        choice.setText(text);
        choice.setTextSize(26);
        choice.setTag(isCorrectAnswer);
        choice.setPadding(40, 20, 40, 20);
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCorrect = (boolean) choice.getTag();
                if (isCorrect){
                    linearLayout.removeView(mulChoicesLayout);
                    addUserMessage(linearLayout, text);
                }
                else{
                    choice.setBackgroundResource(R.drawable.border_green);
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


        TextView continueBtn = v.findViewById(R.id.checkResButton);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCompletion();
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
        npcMessageLayout.setText(indentText(messages[currentMessageIndex++][0]));

        // Add item
        linearLayout.addView(npcLayout);

        // Set responses
        TextView continueBtn = v.findViewById(R.id.checkResButton);
        if (continueBtn == null){
            Log.d("TAG", "addNpcMessage: Null");
            return;
        }
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCompletion();
                renderResponsesForSelecting(linearLayout);
            }
        });
    }

    private void checkCompletion() {
        if (currentMessageIndex == messages.length){
            Intent intent = new Intent(getApplicationContext(), CompleteLessonActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private String indentText(String text) {
        return "      " + text;
    }
}