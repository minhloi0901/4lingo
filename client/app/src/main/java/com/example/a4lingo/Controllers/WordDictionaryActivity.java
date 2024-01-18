package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.DictionaryService;
import com.example.a4lingo.Services.Utils;
import com.example.a4lingo.Services.WordDictionaryService;
import com.example.a4lingo.adapter.DictionarySearchedWordsAdapter;
import com.example.a4lingo.item.WordItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class WordDictionaryActivity extends MainActivity{
    private WordDictionaryService wordDictionaryService = new WordDictionaryService(this);
    private DictionaryService dictionaryService = new DictionaryService(this);
    private DictionarySearchedWordsAdapter adapter;
    private String pronunciation;
    private String audio_url;
    private View v = null;

    @Override
    protected void renderLayout(){
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        root.removeAllViews();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        v = layoutInflater.inflate(R.layout.activity_word_dictionary, root, false);

        // Retrieve data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String word = intent.getStringExtra("WORD");

            if (word == null){
                return;
            }

            ImageView imageView = v.findViewById(R.id.cancelSearchButton);
            imageView.setVisibility(View.VISIBLE);

            EditText editText = v.findViewById(R.id.worddict_searchWordEditText);
            editText.setText(word);

            // Suggest words
            RecyclerView recyclerView = v.findViewById(R.id.searchedWordsRecyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            List<String> words = dictionaryService.getSuggestWords("UserID", word);
            adapter = new DictionarySearchedWordsAdapter(this, words);
            recyclerView.setAdapter(adapter);

            wordDictionaryService.searchWord(word, new Utils.Callback() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread( () -> {
                        try {
//                            System.out.println(response);
                            JSONObject jsonResponse = new JSONObject(response);
                            List<WordItem> wordItemList = wordDictionaryService.parseJsonResponse(jsonResponse);
                            if (wordItemList.size() == 0)
                            {
                                Toast.makeText(WordDictionaryActivity.this, "Search failed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            pronunciation = wordItemList.get(0).getPronunciation();
                            audio_url = wordItemList.get(0).getAudio_url();

                            TextView spellingPronunciation = v.findViewById(R.id.spellingPronunciationTextView);
                            spellingPronunciation.setText(pronunciation);

                            for (WordItem wordItem: wordItemList)
                                renderAnInstance(v, wordItem);

//                            spellingPronunciation.setText();
                            root.addView(v);
                        } catch (JSONException e) {
                            System.out.println("Error parsing JSON in WordDictionaryActivity");
                            e.printStackTrace();
                            Toast.makeText(WordDictionaryActivity.this, "Error parsing JSON in WordDictionaryActivity", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(WordDictionaryActivity.this, "Search failed: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();

        EditText editText = v.findViewById(R.id.worddict_searchWordEditText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = findViewById(R.id.cancelSearchButton);
                imageView.setVisibility(View.VISIBLE);

                RecyclerView recyclerView = findViewById(R.id.searchedWordsRecyclerView);
                if (adapter.getItemCount() > 0){
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update the adapter with the new list of matching words
                List<String> matchingWords = dictionaryService.getSuggestWords("UserID", charSequence.toString());
                adapter.updateSearchedWords(matchingWords);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        v.findViewById(R.id.cancelSearchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);

                RecyclerView recyclerView = findViewById(R.id.searchedWordsRecyclerView);
                recyclerView.setVisibility(View.GONE);

                editText.setText("");
            }
        });

        ImageView audio = v.findViewById(R.id.audioButton);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sound
                try {
                    mediaPlayer.setDataSource("https://api.dictionaryapi.dev/media/pronunciations/en/hello-uk.mp3");
                    // below line is use to prepare
                    // and start our media player.
                    System.out.println("1");
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // Media player is prepared, start playback
                            mediaPlayer.start();
                        }
                    });

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // Release resources when playback is completed
                            mediaPlayer.release();
                        }
                    });

                    mediaPlayer.prepareAsync(); // Use asynchronous prepare to avoid blocking the UI thread


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }



    private void renderAnInstance(View v, WordItem wordItem) {
        LinearLayout rootLayout = v.findViewById(R.id.wordMeaningLinearLayout);

        // Inflate the dictionary word item layout
        LayoutInflater inflater = getLayoutInflater();
        View dictionaryItem = inflater.inflate(R.layout.dictionary_word_item, rootLayout, false);

        // Find views in the inflated layout
        TextView vieMeaning = dictionaryItem.findViewById(R.id.vieMeaning);
        TextView engSen = dictionaryItem.findViewById(R.id.engSentence);
        TextView vieSen = dictionaryItem.findViewById(R.id.vieSentence);

        vieMeaning.setText(wordItem.getMeaning());
        engSen.setText(wordItem.getEngSentence());
        vieSen.setText(wordItem.getVieSentence());

        // Add the inflated view to the root layout
        rootLayout.addView(dictionaryItem);
    }
}
