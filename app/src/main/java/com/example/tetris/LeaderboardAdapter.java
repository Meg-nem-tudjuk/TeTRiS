package com.example.tetris;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {
    private ArrayList<Player> players;

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView score;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.leaderboardName);
            score = itemView.findViewById(R.id.leaderBoardScore);
        }
    }

    public LeaderboardAdapter(ArrayList<Player> p) {
        players = p;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        LeaderboardViewHolder vh = new LeaderboardViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        Player current = players.get(position);
        holder.name.setText("#" + (position + 1) + " " + current.getUsername());
        holder.score.setText(Integer.toString(current.getHighscore()));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
