package com.example.mateusz.fantasy.Home.presenter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mateusz.fantasy.Home.model.UserRank;
import com.example.mateusz.fantasy.R;

import java.util.List;
import java.util.Locale;

public class RVLeagueDetailsAdapter extends RecyclerView.Adapter<RVLeagueDetailsAdapter.LeagueDetailViewHolder> {

    private List<UserRank> mUsers;
    private int userId;

    public RVLeagueDetailsAdapter(List<UserRank> mUsers, int userId) {

        this.mUsers = mUsers;
        this.userId = userId;
    }

    @Override
    public LeagueDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_league_rank, parent, false);
        return new LeagueDetailViewHolder(v);

    }

    @Override
    public void onBindViewHolder(LeagueDetailViewHolder holder, int position) {

        holder.tvTotalPoints.setText(String.format(Locale.ENGLISH,"%d",mUsers.get(position).getTotalPoints()));
        holder.tvRank.setText(String.format(Locale.ENGLISH,"%d",mUsers.get(position).getRank()));
        holder.tvTeamName.setText(mUsers.get(position).getTeamName());
        holder.tvUserName.setText(mUsers.get(position).getFirstName()+" "+mUsers.get(position).getLastName());

        if (userId == mUsers.get(position).getUserId()){
            holder.tvRank.setTextColor(Color.parseColor("#00ca58"));
        }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * RVLeagueDetailsAdapter's viewholder class
     */
    class LeagueDetailViewHolder extends RecyclerView.ViewHolder {

        final TextView tvRank;
        final TextView tvTeamName;
        final TextView tvUserName;
        final TextView tvTotalPoints;


        LeagueDetailViewHolder(View itemView) {

            super(itemView);

            tvRank = itemView.findViewById(R.id.tv_user_rank);
            tvTeamName = itemView.findViewById(R.id.tv_users_team_name);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvTotalPoints = itemView.findViewById(R.id.tv_total_points);

        }

    }
}
