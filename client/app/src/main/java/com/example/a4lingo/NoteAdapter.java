package com.example.a4lingo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.WordViewHolder> {

    private List<WordItem> wordItemList;
    private Context context;

    public NoteAdapter(Context context, List<WordItem> wordItemList) {
        this.context = context;
        this.wordItemList = wordItemList;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        WordItem wordItem = wordItemList.get(position);

        // Set data to views
        holder.textViewWord.setText(wordItem.getWord());
        holder.textViewVietnameseMeaning.setText(wordItem.getMeaning());

        // Set click listener for the ImageView
        holder.imageCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event here
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordItemList.size();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView textViewWord;
        TextView textViewVietnameseMeaning;
        ImageView imageCheckMark;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.textViewWord);
            textViewVietnameseMeaning = itemView.findViewById(R.id.textViewVietnameseMeaning);
            imageCheckMark = itemView.findViewById(R.id.imageCheckMark);
        }
    }
}

