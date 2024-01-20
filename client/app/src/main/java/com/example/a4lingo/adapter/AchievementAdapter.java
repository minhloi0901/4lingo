package com.example.a4lingo.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.item.AchievementItem;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder> {

    private List<AchievementItem> achievementList;
    private Context context;

    public AchievementAdapter(Context context, List<AchievementItem> achievementList) {
        this.context = context;
        this.achievementList = achievementList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AchievementItem achievement = achievementList.get(position);

        // Set data to views
        holder.achievementNameTextView.setText(achievement.getName());
        holder.achievementDescriptionTextView.setText(achievement.getDescription());
        try {
            holder.achievementImageView.setImageResource(achievement.getImageResourceId());
        } catch (Exception e) {
            // If there's an error, set a default image
            holder.achievementImageView.setImageResource(R.drawable.ic_achievement_instance); // Replace 'default_image' with your default image name
        }
        holder.achievementImageView.setImageResource(R.drawable.ic_achievement_instance);
        // Set progress data
        holder.progressBar.setMax(achievement.getTotal());
        holder.progressBar.setProgress(achievement.getProgress());

        // Set progress text
        String progressText = achievement.getProgress() + "/" + achievement.getTotal();
        holder.progressTextView.setText(progressText);

        if (position == getItemCount() - 1){
            holder.seperatedLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return achievementList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView achievementNameTextView;
        TextView achievementDescriptionTextView;
        ImageView achievementImageView;
        ProgressBar progressBar;
        TextView progressTextView;
        TextView seperatedLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            achievementNameTextView = itemView.findViewById(R.id.achievementName);
            achievementDescriptionTextView = itemView.findViewById(R.id.achievementDescription);
            achievementImageView = itemView.findViewById(R.id.achievementImageView);
            progressBar = itemView.findViewById(R.id.horizontalProgressBar);
            progressTextView = itemView.findViewById(R.id.progressTextView);
            seperatedLine = itemView.findViewById(R.id.separatedLine);
        }
    }
}
