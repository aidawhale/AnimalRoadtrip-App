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
import androidx.lifecycle.ViewModelProviders;

import com.aidawhale.tfmarcore.R;
import com.aidawhale.tfmarcore.UserMenuActivity;
import com.aidawhale.tfmarcore.room.AppRoomDatabase;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.SurveyDao;
import com.aidawhale.tfmarcore.utils.DateConverter;

import java.util.Date;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private String userID = "dumb";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
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
        if(userID != null) {
            AppRoomDatabase db = AppRoomDatabase.getDatabase(getContext());
            SurveyDao surveyDao = db.surveyDao();
            Survey survey = surveyDao.getDailySurveyByUser(userID, DateConverter.complexDateToSimpleDate(new Date()));

            String s = "Login " + userID + "\n\n user " + survey.user + "\n date " + survey.date;

            text_userID.setText(s);

        } else {
            text_userID.setText("No data available.");
        }
    }
}