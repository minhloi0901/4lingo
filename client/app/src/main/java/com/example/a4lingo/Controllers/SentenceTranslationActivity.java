package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a4lingo.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SentenceTranslationActivity extends MainActivity {
    private FlexboxLayout sourceContainer;
    private FlexboxLayout destinationContainer;

    protected void renderLayout() {
        super.renderLayout();

        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_sentence_translation, root, false);

        renderAnInstance(v);

        root.addView(v);

    }
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
                boolean isCorrect = true;
                String message = "Tuyệt vời!";
                showBottomSheet(isCorrect, message);
            }
        });
    }

    private void showBottomSheet(boolean isCorrect, String message) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.check_sentence_translation_bottom_sheet_item, null);
        ImageView resStateImgView = bottomSheetView.findViewById(R.id.resultState);
        TextView resMessage = bottomSheetView.findViewById(R.id.resultMessage);

        if (isCorrect)
            resStateImgView.setImageResource(R.drawable.ic_correct);
        else
            resStateImgView.setImageResource(R.drawable.ic_incorrect);

        resMessage.setText(message);


        TextView continueBtn = bottomSheetView.findViewById(R.id.continueButton);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check
                // If it is last question, end ...
                // Else move to the next question
                Intent intent = new Intent(getApplicationContext(), SentenceTranslationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                int qIndex = 6;
                intent.putExtra("Q_INDEX", qIndex);
                startActivity(intent);
            }
        });

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void renderAnInstance(View v) {
        sourceContainer = v.findViewById(R.id.sourceContainer);
        destinationContainer = v.findViewById(R.id.destinationContainer);

        // Initialize words in the source container
        addWordToContainer("Tôi", sourceContainer);
        addWordToContainer("cười", sourceContainer);
        addWordToContainer("bởi", sourceContainer);
        addWordToContainer("vì", sourceContainer);
        addWordToContainer("bạn", sourceContainer);
        addWordToContainer("cười", sourceContainer);
        addWordToContainer("cho", sourceContainer);
        addWordToContainer("thấy", sourceContainer);
        addWordToContainer("Cô", sourceContainer);
    }

    private void addWordToContainer(String word, FlexboxLayout container) {
        // Inflate the word item layout
        View wordFrame = getLayoutInflater().inflate(R.layout.word_item, container, false);

        // Set the word text
        TextView wordTextView = wordFrame.findViewById(R.id.wordTextView);
        wordTextView.setText(word);

        wordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Determine the source and destination containers
                FlexboxLayout source = (FlexboxLayout) wordFrame.getParent();
                FlexboxLayout destination = (source == sourceContainer) ? destinationContainer : sourceContainer;

                // Move the word to the destination container
                if (source == sourceContainer) {
                    TextView wordTextView = wordFrame.findViewById(R.id.wordTextView);
                    wordTextView.setVisibility(View.INVISIBLE);

                    // Create a copy of the wordItem
                    View wordFrameCopy = getLayoutInflater().inflate(R.layout.word_item, destination, false);
                    TextView wordTextViewCopy = wordFrameCopy.findViewById(R.id.wordTextView);
                    wordTextViewCopy.setText(wordTextView.getText());

                    // Store the original index of the word in the source container as a tag
                    wordFrameCopy.setTag(source.indexOfChild(wordFrame));
                    wordTextViewCopy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FlexboxLayout source = (FlexboxLayout) wordFrameCopy.getParent();
                            FlexboxLayout destination = (source == sourceContainer) ? destinationContainer : sourceContainer;

                            source.removeView(wordFrameCopy);

                            // Retrieve the original index from the tag
                            int originalIndex = (int) wordFrameCopy.getTag();

                            // Retrieve the original item from the source container using the stored index
                            View movedItem = destination.getChildAt(originalIndex);
                            TextView word = movedItem.findViewById(R.id.wordTextView);
                            word.setVisibility(View.VISIBLE);
                        }
                    });

                    destination.addView(wordFrameCopy);
                }
            }
        });

        // Add the word item to the container
        container.addView(wordFrame);
    }
}
