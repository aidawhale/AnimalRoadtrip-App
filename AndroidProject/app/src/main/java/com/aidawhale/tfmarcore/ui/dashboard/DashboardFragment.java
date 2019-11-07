package com.aidawhale.tfmarcore.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.aidawhale.tfmarcore.utils.DateConverter;

import java.util.Date;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private String userID = "dumb";

    private DashboardFragmentViewModel dashboardFragmentViewModel;

    private ImageView image_happyFace;
    private ImageView image_foodFace;
    private ImageView image_painFace;
    private TextView tv_dailyStepsNumber;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        dashboardFragmentViewModel = new ViewModelProvider(this).get(DashboardFragmentViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userID = UserMenuActivity.getUserID();

        if(userID == null) {
            return;
        }

        image_happyFace = getView().findViewById(R.id.image_happiness_face);
        image_foodFace = getView().findViewById(R.id.image_food_face);
        image_painFace = getView().findViewById(R.id.image_pain_face);
        tv_dailyStepsNumber = getView().findViewById(R.id.text_daily_steps_number);


        String date = DateConverter.complexDateToSimpleDate(new Date());
        dashboardFragmentViewModel.getDailySurveyByUser(userID, date).observe(getViewLifecycleOwner(), new Observer<Survey>() {
            @Override
            public void onChanged(Survey survey) {
                if(survey == null) {
                    return;
                }

                if(image_happyFace != null) {
                    image_happyFace.setImageResource(getFaceFromNumber(survey.happiness));
                }
                if(image_foodFace != null) {
                    image_foodFace.setImageResource(getFaceFromNumber(survey.food));
                }
                if(image_painFace != null) {
                    image_painFace.setImageResource(getFaceFromNumber(survey.pain));
                }
            }
        });

        dashboardFragmentViewModel.getDailyStepCount(userID, date).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(tv_dailyStepsNumber != null && integer != null ) {
                    tv_dailyStepsNumber.setText(String.valueOf(integer));
                }
            }
        });

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

    private int getFaceFromNumber(int faceNumber) {
        switch (faceNumber) {
            case 0:
                return R.drawable.ic_cara_muy_triste;
            case 1:
                return R.drawable.ic_cara_triste;
            case 2:
                return R.drawable.ic_cara_base;
            case 3:
                return R.drawable.ic_cara_feliz;
            case 4:
                return R.drawable.ic_cara_muy_feliz;
        }
        return R.drawable.ic_do_not_disturb;
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