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

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private List<RankingItem> rankingItemList;
    private Context context;

    public RankingAdapter(List<RankingItem> rankingItemList, Context context) {
        this.rankingItemList = rankingItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ranking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankingItem item = rankingItemList.get(position);

        holder.rankTextView.setText(String.valueOf(item.getRank()));
        holder.avatarImageView.setImageResource(item.getAvatarResId());
        holder.nameTextView.setText(item.getName());
        holder.scoreTextView.setText(String.valueOf(item.getScore()));
    }

    @Override
    public int getItemCount() {
        return rankingItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rankTextView, nameTextView, scoreTextView;
        ImageView avatarImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.rankTextView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}

