package com.aidawhale.tfmarcore.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aidawhale.tfmarcore.R;
import com.aidawhale.tfmarcore.UserMenuActivity;
import com.aidawhale.tfmarcore.room.Game;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.ViewModels.DashboardFragmentViewModel;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private String userID = "dumb";

    private DashboardFragmentViewModel dashboardFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        dashboardFragmentViewModel = new ViewModelProvider(this).get(DashboardFragmentViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userID = UserMenuActivity.getUserID();

        TextView text_userID = getView().findViewById(R.id.text_userID);

        if(userID == null) {
            text_userID.setText("No data available.");
            return;
        }

        String s = "Login " + userID;
        text_userID.setText(s);

        dashboardFragmentViewModel.getUserSurveys(userID).observe(getViewLifecycleOwner(), new Observer<List<Survey>>() {
            @Override
            public void onChanged(List<Survey> surveys) {
                updateDashboardSurveyData(surveys);
            }
        });

        dashboardFragmentViewModel.getUserGames(userID).observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                updateDashboardGameStatistics(games);
            }
        });



    }

    private void updateDashboardSurveyData(List<Survey> surveys) {
        if(surveys.size() == 0) {
            return;
        }

        // TODO: update survey data
    }

    private void updateDashboardGameStatistics(List<Game> games) {
        if(games.size() == 0) {
            return;
        }

        // TODO: update game data
    }
}