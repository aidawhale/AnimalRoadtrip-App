package com.aidawhale.tfmarcore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SurveyRecyclerViewAdapter extends RecyclerView.Adapter<SurveyRecyclerViewAdapter.SurveyViewHolder> {

    private static final String TAG = "SurveyRecyclerViewAdapt";

    private Context context;
    private ArrayList<Integer> questionTitles = new ArrayList<>();
    private ArrayList<Integer> questions = new ArrayList<>();

    public SurveyRecyclerViewAdapter(Context context, ArrayList<Integer> questionTitles, ArrayList<Integer> questions) {
        this.context = context;
        this.questionTitles = questionTitles;
        this.questions = questions;
    }

    @NonNull
    @Override
    public SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_listitem, parent, false);
        SurveyViewHolder holder = new SurveyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyViewHolder holder, int position) {
        // Load info on each list item
        holder.questionTitle.setText(questionTitles.get(position));
        holder.question.setText(questions.get(position));

        /// If onClickListeners are needed, they would be set here...
    }

    @Override
    public int getItemCount() {
        return questionTitles.size();
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout parentLayout;
        TextView questionTitle;
        TextView question;

        public SurveyViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.survey_item_parent_layout);
            questionTitle = itemView.findViewById(R.id.survey_item_question_title);
            question = itemView.findViewById(R.id.survey_item_question);
        }
    }
}
