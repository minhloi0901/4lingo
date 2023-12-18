package com.example.a4lingo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.item.MistakeItem;
import com.example.a4lingo.R;

import java.util.List;

public class MistakeAdapter extends RecyclerView.Adapter<MistakeAdapter.ViewHolder> {

    private List<MistakeItem> mistakeList;
    private Context context;

    public MistakeAdapter(List<MistakeItem> mistakeList, Context context) {
        this.mistakeList = mistakeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mistake_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MistakeItem mistake = mistakeList.get(position);

        holder.textViewAssignmentType.setText("• " + mistake.getAssignmentType());
        holder.textViewMistakeDescription.setText(mistake.getMistakeDescription());

        holder.imageViewPlay.setOnClickListener(v -> {
            // Handle playback here
            // You can launch a new activity or perform any action to play the assignment
        });
    }

    @Override
    public int getItemCount() {
        return mistakeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAssignmentType;
        TextView textViewMistakeDescription;
        ImageView imageViewPlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAssignmentType = itemView.findViewById(R.id.textViewAssignmentType);
            textViewMistakeDescription = itemView.findViewById(R.id.textViewMistakeDescription);
            imageViewPlay = itemView.findViewById(R.id.imageViewPlay);
        }
    }
}

