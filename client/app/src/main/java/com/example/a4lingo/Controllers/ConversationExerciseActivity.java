package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a4lingo.R;

public class ConversationExerciseActivity extends MainActivity{
    @Override
    protected void renderLayout() {
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_conversation_exercise, root, false);

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

        TextView checkResBtn = findViewById(R.id.checkResButton);
        checkResBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void renderAnInstance(View v) {
        LinearLayout linearLayout = v.findViewById(R.id.messagesLayout);

        String text = "Hi! What can I do for you?";
        addNpcMessage(linearLayout, text);

        int npcMessageIndex = 1;
        renderResponsesForSelecting(linearLayout, npcMessageIndex);

        text = "Hi Professor. I wanted to talk to you about my grade for the last assignment.";
        addUserMessage(linearLayout, text);
    }

    private void renderResponsesForSelecting(LinearLayout linearLayout, int npcMessageIndex) {
        LinearLayout mulChoicesLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.vertical_multiple_choices_layout, linearLayout, false);

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
    }

    private void addNpcMessage(LinearLayout linearLayout, String text) {
        LinearLayout npcLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.conversation_npc_item, linearLayout, false);

        ImageView audioBtn = npcLayout.findViewById(R.id.audioButton);
        audioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start Audio
            }
        });
        TextView npcMessageLayout = npcLayout.findViewById(R.id.messageTextView);
        npcMessageLayout.setText(indentText(text));

        linearLayout.addView(npcLayout);
    }

    private String indentText(String text) {
        return "      " + text;
    }
}
