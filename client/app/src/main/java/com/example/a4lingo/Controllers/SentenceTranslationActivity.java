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

import com.example.a4lingo.R;
import com.example.a4lingo.Services.SentenceTranslationService;
import com.example.a4lingo.Services.Utils;
import com.example.a4lingo.item.TranslationQuestion;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SentenceTranslationActivity extends MainActivity {
    LinearLayout root;
    View v;

    private int questionIndex = 0;
    private int total_score = 0;
    private FlexboxLayout sourceContainer;
    private FlexboxLayout destinationContainer;
    private long startTimeMillis;
    private String originalSentence;
    private String destinationSentence;
    private List<String> possibleWords = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialog;
    private int correctCount = 0;

    private List<TranslationQuestion> questions = null;
    protected void renderLayout() {
        super.renderLayout();
        startTimeMillis = SystemClock.elapsedRealtime();

        root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        v = layoutInflater.inflate(R.layout.activity_sentence_translation, root, false);

        if(questions == null){
            SentenceTranslationService sentenceTranslationService = new SentenceTranslationService(getApplicationContext());
            String token = Utils.getToken(getApplicationContext());
            if (token != null){
                sentenceTranslationService.getTranslationQuestions(token, 2, new Utils.Callback() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            System.out.println(response);
                            JSONArray jsonResponse = new JSONArray(response);

                            questions = sentenceTranslationService.parseJsonResponse(jsonResponse);
                            setQuestion(questions.get(questionIndex));

                            renderAnInstance(v);
                        } catch (JSONException e) {
                            System.out.println("Error parsing JSON in SentenceTranslationActivity");
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON in SentenceTranslationActivity", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            setQuestion(questions.get(questionIndex));
        }


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

        TextView checkResBtn = findViewById(R.id.continueButton);
        checkResBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String translatedSentence = getTranslatedSentence(destinationContainer);
                Log.i("Translated Sentence: ", translatedSentence);
                if (getTranslatedSentence(destinationContainer).equals(destinationSentence)){
                    String message = "Tuyệt vời!";
                    bottomSheetDialog = Utils.showBottomSheet(SentenceTranslationActivity.this, true, message);
                    correctCount++;
                    total_score += questions.get(questionIndex).getScore();
                }
                else {
                    String message = "Cố gắng hơn nữa nhé!";
                    bottomSheetDialog = Utils.showBottomSheet(SentenceTranslationActivity.this, false, message);
                }
                if (!isFinishing())
                    bottomSheetDialog.show();

                TextView continueBtn = bottomSheetDialog.findViewById(R.id.continueButton);
                assert continueBtn != null;
                continueBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questionIndex++;
                        boolean completed = Utils.checkCompletion(questionIndex, questions, startTimeMillis, SentenceTranslationActivity.this, correctCount, total_score, 2);
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
        });
    }

    // Function to set the current question
    private void setQuestion(TranslationQuestion question) {
        startTimeMillis = SystemClock.elapsedRealtime();
        originalSentence = question.getOriginalSentence();
        destinationSentence = question.getDestinationSentence();
        possibleWords = question.getPossibleWords();
        // Print the details
        System.out.println("Start Time: " + startTimeMillis);
        System.out.println("Original Sentence: " + originalSentence);
        System.out.println("Destination Sentence: " + destinationSentence);
        System.out.println("Possible Words: " + possibleWords);
    }

    private void renderAnInstance(View v) {
        sourceContainer = v.findViewById(R.id.sourceContainer);
        destinationContainer = v.findViewById(R.id.destinationContainer);

        sourceContainer.removeAllViews();
        destinationContainer.removeAllViews();

        setQuestion(questions.get(questionIndex));

        TextView originalSentenceTextView = v.findViewById(R.id.originalSentence);
        originalSentenceTextView.setText(originalSentence);

        TextView progressTextView = v.findViewById(R.id.progressTextView);
        progressTextView.setText((questionIndex + 1)  + "/" + questions.size());

        // Initialize words in the source container
        for (String word: possibleWords)
            addWordToContainer(word, sourceContainer);
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



    private String getTranslatedSentence(FlexboxLayout container) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
           if (child instanceof LinearLayout) {

                // If the child is a TextView, append its text to the StringBuilder
                stringBuilder.append(((TextView) child.findViewById(R.id.wordTextView)).getText().toString());

                // Optionally, add a space between words
                stringBuilder.append(" ");
            }
        }

        return stringBuilder.toString().trim();
    }
}
