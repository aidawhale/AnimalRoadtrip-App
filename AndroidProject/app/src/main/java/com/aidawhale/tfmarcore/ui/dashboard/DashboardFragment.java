package com.aidawhale.tfmarcore.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aidawhale.tfmarcore.R;
import com.aidawhale.tfmarcore.UserMenuActivity;
import com.aidawhale.tfmarcore.room.Survey;
import com.aidawhale.tfmarcore.room.ViewModels.DashboardFragmentViewModel;
import com.aidawhale.tfmarcore.utils.DateConverter;
import com.aidawhale.tfmarcore.utils.GameInfoPerDay;
import com.aidawhale.tfmarcore.utils.LanguageSettings;
import com.aidawhale.tfmarcore.utils.UserStepsPerGame;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
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

    private PieChart dailyStepsPieChart;
    private CombinedChart gameCombinedChart;
    private LineChart surveyLineChart;

    private int black, blue, green, yellow, orange, purple;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        dashboardFragmentViewModel = new ViewModelProvider(this).get(DashboardFragmentViewModel.class);

        LanguageSettings.updateLocale(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_dashboard);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        black = ContextCompat.getColor(getContext(), R.color.colorBlack);
        blue = ContextCompat.getColor(getContext(), R.color.colorBlue);
        green = ContextCompat.getColor(getContext(), R.color.colorGreen);
        yellow = ContextCompat.getColor(getContext(), R.color.colorYellow);
        orange = ContextCompat.getColor(getContext(), R.color.colorOrange);
        purple = ContextCompat.getColor(getContext(), R.color.colorPurple);

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

        dailyStepsPieChart = getView().findViewById(R.id.chart_daily_steps_per_game);
        gameCombinedChart = getView().findViewById(R.id.chart_steps_and_time_per_day);
        surveyLineChart = getView().findViewById(R.id.chart_survey);

        configCharts();

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

        dashboardFragmentViewModel.getUserStepsAndTimePerDay(userID).observe(getViewLifecycleOwner(), new Observer<List<GameInfoPerDay>>() {
            @Override
            public void onChanged(List<GameInfoPerDay> gameInfoPerDays) {
                updateStepsAndTimePerDay(gameInfoPerDays);
            }
        });

        dashboardFragmentViewModel.getDailyUserStepsPerGameType(userID, date).observe(getViewLifecycleOwner(), new Observer<List<UserStepsPerGame>>() {
            @Override
            public void onChanged(List<UserStepsPerGame> userStepsPerGames) {
                updateUserStepsPerGame(userStepsPerGames);
            }
        });
    }

    private void configCharts() {
        Legend legend;
        YAxis leftAxis;
        YAxis rightAxis;
        XAxis xAxis;

        dailyStepsPieChart.getDescription().setEnabled(false);
        gameCombinedChart.getDescription().setEnabled(false);
        surveyLineChart.getDescription().setEnabled(false);

        // Steps pieChart
        legend = dailyStepsPieChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(12f);
        dailyStepsPieChart.setDrawEntryLabels(false);
        dailyStepsPieChart.setHoleRadius(40f);

        // Game combinedChart
        legend = gameCombinedChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(12f);
        rightAxis = gameCombinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setGranularity(1f);
        leftAxis = gameCombinedChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        xAxis = gameCombinedChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        // Survey lineChart
        legend = surveyLineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(12f);
        rightAxis = surveyLineChart.getAxisRight();
        rightAxis.setGranularity(1f);
        rightAxis.setAxisMinimum(0f);
        leftAxis = surveyLineChart.getAxisLeft();
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f);
        surveyLineChart.getXAxis().setGranularity(1f);
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

        // Update surveyLineChart
        List<Entry> hEntries = new ArrayList<>();
        List<Entry> fEntries = new ArrayList<>();
        List<Entry> pEntries = new ArrayList<>();

        final List<String> dates = new ArrayList<>();

        int date = 0;
        for (Survey s : surveys) {
            dates.add(s.date.split("-")[2]); // get day from date
            hEntries.add(new Entry(date, s.happiness));
            fEntries.add(new Entry(date, s.food));
            pEntries.add(new Entry(date, s.pain));
            date++;
        }

        LineDataSet hDataSet = new LineDataSet(hEntries, getContext().getResources().getString(R.string.happiness));
        LineDataSet fDataSet = new LineDataSet(fEntries, getContext().getResources().getString(R.string.food));
        LineDataSet pDataSet = new LineDataSet(pEntries, getContext().getResources().getString(R.string.pain));

        hDataSet.setColor(yellow);
        fDataSet.setColor(green);
        pDataSet.setColor(purple);
        hDataSet.setLineWidth(2.0f);
        fDataSet.setLineWidth(2.0f);
        pDataSet.setLineWidth(2.0f);
        hDataSet.setCircleColor(yellow);
        fDataSet.setCircleColor(green);
        pDataSet.setCircleColor(purple);
        hDataSet.setCircleRadius(5f);
        fDataSet.setCircleRadius(5f);
        pDataSet.setCircleRadius(5f);
        hDataSet.setFillColor(yellow);
        fDataSet.setFillColor(green);
        pDataSet.setFillColor(purple);
        hDataSet.setDrawValues(false);
        fDataSet.setDrawValues(false);
        pDataSet.setDrawValues(false);
        hDataSet.setValueTextColor(yellow);
        fDataSet.setValueTextColor(green);
        pDataSet.setValueTextColor(purple);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(hDataSet);
        dataSets.add(fDataSet);
        dataSets.add(pDataSet);
        LineData data = new LineData(dataSets);
        surveyLineChart.setData(data);
        XAxis xAxis = surveyLineChart.getXAxis();
        // Swap xAxis labels to correct day number
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if(value >= 0 && value < dates.size()) {
                    return dates.get((int) value);
                } else {
                    return "";
                }
            }
        });
        surveyLineChart.invalidate(); // refresh chart

    }

    private void updateStepsAndTimePerDay(List<GameInfoPerDay> gameInfoPerDays) {
        if(gameInfoPerDays.size() == 0) {
            return;
        }

        // Update gameCombinedChart
        List<BarEntry> stepsEntries = new ArrayList<>();
        List<Entry> timeEntries = new ArrayList<>();

        final List<String> dates = new ArrayList<>();

        int date = 0;
        for (GameInfoPerDay info : gameInfoPerDays) {
            dates.add(info.date.split("-")[2]); // get day from date
            stepsEntries.add(new BarEntry(date, info.sumSteps));
            timeEntries.add(new Entry(date, info.sumTime/60));
            date++;
        }

        BarDataSet sDataSet = new BarDataSet(stepsEntries, getContext().getString(R.string.steps));
        sDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        sDataSet.setColor(blue);
        BarData sData = new BarData();
        sData.addDataSet(sDataSet);

        LineDataSet tDataSet = new LineDataSet(timeEntries, getContext().getString(R.string.minutes));
        tDataSet.setColor(orange);
        tDataSet.setLineWidth(2.5f);
        tDataSet.setCircleColor(orange);
        tDataSet.setCircleRadius(5f);
        tDataSet.setFillColor(orange);
        tDataSet.setDrawValues(true);
        tDataSet.setValueTextSize(10f);
        tDataSet.setValueTextColor(orange);
        tDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        LineData tData = new LineData();
        tData.addDataSet(tDataSet);

        CombinedData data = new CombinedData();
        data.setData(sData);
        data.setData(tData);

        gameCombinedChart.setData(data);
        XAxis xAxis = gameCombinedChart.getXAxis();
        xAxis.setAxisMinimum(xAxis.getAxisMinimum() - 0.5f);
        xAxis.setAxisMaximum(xAxis.getAxisMaximum() + 0.5f);
        // Swap xAxis labels to correct day number
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if(value >= 0 && value < dates.size()) {
                    return dates.get((int) value);
                } else {
                    return "";
                }
            }
        });
        gameCombinedChart.invalidate();
    }

    private void updateUserStepsPerGame(List<UserStepsPerGame> userStepsPerGames) {
        if(userStepsPerGames.size() == 0) {
            return;
        }

        List<PieEntry> dailyStepsEntries = new ArrayList<>();


        String gameName = "";
        for (UserStepsPerGame steps : userStepsPerGames) {
            switch (steps.game_type) {
                case 1:
                    gameName = getContext().getString(R.string.first_game_name);
                    break;
                case 2:
                    gameName = getContext().getString(R.string.second_game_name);
                    break;
                case 3:
                    gameName = getContext().getString(R.string.third_game_name);
                    break;
            }
            dailyStepsEntries.add(new PieEntry(steps.sumSteps, gameName));
        }

        List<Integer> colors = new ArrayList<>();
        colors.add(yellow);
        colors.add(orange);
        colors.add(purple);

        PieDataSet pieDataSet = new PieDataSet(dailyStepsEntries, "");
        pieDataSet.setValueTextSize(13f);
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        dailyStepsPieChart.setData(pieData);
        dailyStepsPieChart.invalidate();
    }
}