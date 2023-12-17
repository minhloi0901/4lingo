package com.example.a4lingo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.item.ContestItem;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ContestItemAdapter extends RecyclerView.Adapter<ContestItemAdapter.ViewHolder> {

    private List<ContestItem> contestItemList;

    public ContestItemAdapter(List<ContestItem> contestItemList) {
        this.contestItemList = contestItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContestItem contestItem = contestItemList.get(position);
        holder.contestName.setText(contestItem.getContestName());
        holder.remainingTime.setText(getRemainingTime(contestItem));
    }

    @Override
    public int getItemCount() {
        return contestItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contestName, remainingTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contestName = itemView.findViewById(R.id.contestName);
            remainingTime = itemView.findViewById(R.id.remainingTime);
        }
    }

    protected String getRemainingTime(ContestItem item) {
        long currentTime = System.currentTimeMillis();
        long timeCreated = item.getTimeCreated().getTime();
        long durationMillis = TimeUnit.MINUTES.toMillis(item.getDuration());
        long remainingMillis = durationMillis - (currentTime - timeCreated);

        // Ensure remaining time is not negative
        remainingMillis = Math.max(remainingMillis, 0);

        // Format remaining time in hours and minutes
        long hours = TimeUnit.MILLISECONDS.toHours(remainingMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(remainingMillis) - TimeUnit.HOURS.toMinutes(hours);
        return "Thời gian còn lại là " + String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);
    }
}
