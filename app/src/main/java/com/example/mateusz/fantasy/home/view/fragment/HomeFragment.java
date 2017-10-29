package com.example.mateusz.fantasy.home.view.fragment;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mateusz.fantasy.authentication.login.view.LoginActivity;
import com.example.mateusz.fantasy.home.model.repo.HomeData;
import com.example.mateusz.fantasy.home.model.repo.HomeUser;
import com.example.mateusz.fantasy.home.presenter.HomePresenter;
import com.example.mateusz.fantasy.R;
import com.example.mateusz.fantasy.home.view.UserDetailActivity;
import com.example.mateusz.fantasy.utils.NetworkUtils;


import static android.content.Context.MODE_PRIVATE;
import static com.example.mateusz.fantasy.authentication.login.view.LoginActivity.PREFS_NAME;
import static com.example.mateusz.fantasy.authentication.login.view.LoginActivity.TOTAL_POINTS_EXTRA;
import static com.example.mateusz.fantasy.authentication.login.view.LoginActivity.USER_ID_EXTRA;


public class HomeFragment extends Fragment implements ParentFragment, IHomeView{

    public static final String FIRST_NAME_EXTRA = "first_name";
    public static final String LAST_NAME_EXTRA = "last_name";
    public static final String EMAIL_EXTRA = "email";
    public static final String PASSWORD_EXTRA = "password";
    public static final String BUNDLE_EXTRA = "bundle";

    //UI
    public FrameLayout fragmentContainer;
    private TextView mTvUserName;
    private TextView mTvUserTeamName;
    private TextView mTvGw;
    private TextView mTvUserScore;
    private TextView mTvAverageScore;
    private TextView mTvHHighestScore;
    private TextView mTvDeadlineDate;
    private ImageView mIvSettings;

    //Dependencies
    private HomePresenter mHomePresenter;

    public Button mBtnLogOut;

    private int mUserId;
    private int mTeamId;
    private HomeUser mHomeUser;

    public HomeFragment() {
        // Required empty public constructor
        if (mHomePresenter == null) {
            mHomePresenter = new HomePresenter(this);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fragmentContainer = view.findViewById(R.id.home_fragment_container);

        initTextViews(view);
        initButton(view);
        getLoggedUserId();

        mHomePresenter.initUser(mUserId);

        return view;
    }


    /**
     * Called when a fragment will be displayed
     */
    @Override
    public void willBeDisplayed() {

        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }

    }

    /**
     * Called when a fragment will be hidden
     */
    @Override
    public void willBeHidden() {

        if (fragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            fragmentContainer.startAnimation(fadeOut);
        }

    }

    @Override
    public void getUser(HomeUser user) {

        mTvUserName.setText(user.getFirstName()+" "+user.getLastName());
        mTvUserTeamName.setText(user.getTeamName());
        mTeamId = user.getTeamId();
        mHomeUser = user;
        mHomePresenter.getHomeData(mTeamId);

    }

    @Override
    public void showConnectionError() {

        NetworkUtils.showConnectionErrorToast(getActivity());

    }

    @Override
    public void presentData(HomeData body) {

        String gw = getString(R.string.gw_gameweek) + " " + body.getGw() + " " + getString(R.string.gw_points);
        mTvGw.setText(gw);
        mTvUserScore.setText(Integer.toString(body.getYourScore()));
        mTvAverageScore.setText(Integer.toString((int)body.getAvg()));
        mTvHHighestScore.setText(Integer.toString(body.getMax()));
        mTvDeadlineDate.setText(body.getDeadline());

    }

    private void initButton(View view) {

        mBtnLogOut = view.findViewById(R.id.btn_logout);
        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt(USER_ID_EXTRA, 0);
                editor.putInt(TOTAL_POINTS_EXTRA,0);
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    private void initTextViews(View view){

        mTvUserName = view.findViewById(R.id.tv_home_user_name);
        mTvUserTeamName = view.findViewById(R.id.tv_home_team_name);
        mTvGw = view.findViewById(R.id.tv_home_gw);
        mTvUserScore = view.findViewById(R.id.tv_home_points);
        mTvAverageScore = view.findViewById(R.id.tv_average_points);
        mTvHHighestScore = view.findViewById(R.id.tv_highest_points);
        mTvDeadlineDate = view.findViewById(R.id.tv_home_deadline);

        mIvSettings = view.findViewById(R.id.iv_settings);
        mIvSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FIRST_NAME_EXTRA,mHomeUser.getFirstName());
                bundle.putString(LAST_NAME_EXTRA,mHomeUser.getLastName());
                bundle.putString(EMAIL_EXTRA,mHomeUser.getEmail());
                bundle.putString(PASSWORD_EXTRA,mHomeUser.getPassword());

                Intent intent = new Intent(getContext(),UserDetailActivity.class);
                intent.putExtra(BUNDLE_EXTRA,bundle);

                getContext().startActivity(intent);
            }

        });

    }


    private void getLoggedUserId(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        mUserId =  sharedPreferences.getInt(USER_ID_EXTRA, 0);

    }

}
