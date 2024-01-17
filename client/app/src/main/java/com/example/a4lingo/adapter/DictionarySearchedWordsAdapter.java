package com.example.a4lingo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Controllers.WordDictionaryActivity;

import java.util.List;

public class DictionarySearchedWordsAdapter extends RecyclerView.Adapter<DictionarySearchedWordsAdapter.ViewHolder> {
    private Context context;
    private List<String> words;

    public DictionarySearchedWordsAdapter(Context context, List<String> words) {
        this.context = context;
        this.words = words;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String word = words.get(position);
        holder.searchedWordTextView.setText(word);
        // Bind other data or perform actions here
        holder.forwardToWordPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WordDictionaryActivity.class);
                intent.putExtra("WORD", word);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView searchedWordTextView;
        ImageView forwardToWordPageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchedWordTextView = itemView.findViewById(R.id.searchedWord);
            forwardToWordPageButton = itemView.findViewById(R.id.forwardToWordPageButton);
        }
    }

    // Add this method to update the list of searched words
    public void updateSearchedWords(List<String> newSearchedWords) {
        words = newSearchedWords;
        notifyDataSetChanged();
    }
}
