package com.example.mateusz.fantasy.Home.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mateusz.fantasy.Home.model.League;
import com.example.mateusz.fantasy.Home.model.UserRank;
import com.example.mateusz.fantasy.Home.presenter.RVLeagueDetailsAdapter;
import com.example.mateusz.fantasy.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mateusz.fantasy.Home.model.League.CODE;
import static com.example.mateusz.fantasy.Home.model.League.LEAGUE_ID;
import static com.example.mateusz.fantasy.Home.model.League.NAME;
import static com.example.mateusz.fantasy.Home.model.League.NUMBER_OF_PLAYERS;
import static com.example.mateusz.fantasy.Home.model.League.USER_POSITION;
import static com.example.mateusz.fantasy.Home.presenter.LeaguePresenter.LEAGUE_BUNDLE_EXTRA;

public class LeagueDetailActivity extends AppCompatActivity {

    private League league;

    @BindView(R.id.tv_league_name_detail)
    TextView mTvLeagueName;
    @BindView(R.id.tv_ranking_detail)
    TextView mTvRanking;
    @BindView(R.id.tv_code_detail)
    TextView mTvCode;
    @BindView(R.id.tv_number_of_users)
    TextView mTvNumberOfPlayers;
    @BindView(R.id.iv_share)
    ImageView mIvShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        getLeagueFromIntent(intent);

        initializeView();

        initializeRecyclerView();


    }

    private void getLeagueFromIntent(Intent intent){

        Bundle bundle = intent.getBundleExtra(LEAGUE_BUNDLE_EXTRA);

        league = new League(bundle.getString(NAME),bundle.getInt(USER_POSITION),bundle.getString(CODE),bundle.getInt(LEAGUE_ID),bundle.getInt(NUMBER_OF_PLAYERS));

    }

    /**
     * Initialize view with data form intent
     */
    private void initializeView(){
        mTvLeagueName.setText(league.getName());
        mTvRanking.setText(String.format(Locale.ENGLISH,"%d",league.getUserPosition()));
        mTvCode.setText(league.getCode());
        mTvNumberOfPlayers.setText(String.format(Locale.ENGLISH,"%d",league.getNumberOfPlayers()));

        mIvShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_league_text_pt1) + " \""+mTvLeagueName.getText().toString()+"\"" + getString(R.string.share_league_text_pt2) + mTvCode.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }

        });
    }

    /**
     * Initialize RecyclerView
     */
    private void initializeRecyclerView(){

        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.rv_league_detail);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        RVLeagueDetailsAdapter rvLeagueDetailsAdapter = new RVLeagueDetailsAdapter(UserRank.initializeData());
        recyclerView.setAdapter(rvLeagueDetailsAdapter);

        recyclerView.setHasFixedSize(true);
    }
}
