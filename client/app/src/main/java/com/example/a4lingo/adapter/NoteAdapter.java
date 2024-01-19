package com.example.a4lingo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.NoteService;
import com.example.a4lingo.Services.Utils;
import com.example.a4lingo.item.WordItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                String token = Utils.getToken(context);
                if (token != null){
                    NoteService noteService = new NoteService(context);
                    noteService.deleteAWord(token, wordItem.getWord(), new Utils.Callback() {
                        @Override
                        public void onSuccess(String response) {
                            // Use getAdapterPosition() to get the current position
                            int clickedPosition = holder.getAdapterPosition();

                            // Check if the position is valid before removing the item
                            if (clickedPosition != RecyclerView.NO_POSITION) {
                                removeItem(clickedPosition); // Remove the clicked item from the list
                                notifyDataSetChanged(); // Notify the adapter that the data set has changed
                            }
                        }
                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void removeItem(int position) {
        if (position >= 0 && position < wordItemList.size()) {
            wordItemList.remove(position);
        }
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

