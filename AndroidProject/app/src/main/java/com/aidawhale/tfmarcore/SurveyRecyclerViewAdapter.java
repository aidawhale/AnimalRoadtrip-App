package com.aidawhale.tfmarcore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SurveyRecyclerViewAdapter extends RecyclerView.Adapter<SurveyRecyclerViewAdapter.SurveyViewHolder> {

    private static final String TAG = "SurveyRecyclerViewAdapt";

    private Context context;
    private ArrayList<Integer> questionTitles;
    private ArrayList<Integer> questions;

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
    public void onBindViewHolder(@NonNull final SurveyViewHolder holder, final int position) {
        // Load info on each list item
        holder.questionTitle.setText(questionTitles.get(position));
        holder.question.setText(questions.get(position));
        SurveyActivity.values.put(questionTitles.get(position), null);

        // Set onClickListener for each face
        holder.verysad_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurveyActivity.values.put(questionTitles.get(position), "0");
                resetFaceColor(holder);
                holder.verysad_face.setColorFilter(context.getColor(R.color.colorPrimaryDark));
            }
        });
        holder.sad_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurveyActivity.values.put(questionTitles.get(position), "1");
                resetFaceColor(holder);
                holder.sad_face.setColorFilter(context.getColor(R.color.colorPrimaryDark));
            }
        });
        holder.base_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurveyActivity.values.put(questionTitles.get(position), "2");
                resetFaceColor(holder);
                holder.base_face.setColorFilter(context.getColor(R.color.colorPrimaryDark));
            }
        });
        holder.happy_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurveyActivity.values.put(questionTitles.get(position), "3");
                resetFaceColor(holder);
                holder.happy_face.setColorFilter(context.getColor(R.color.colorPrimaryDark));
            }
        });
        holder.veryhappy_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurveyActivity.values.put(questionTitles.get(position), "4");
                resetFaceColor(holder);
                holder.veryhappy_face.setColorFilter(context.getColor(R.color.colorPrimaryDark));
            }
        });
    }

    private void resetFaceColor(SurveyViewHolder holder) {
        holder.verysad_face.setColorFilter(context.getColor(R.color.colorGray));
        holder.sad_face.setColorFilter(context.getColor(R.color.colorGray));
        holder.base_face.setColorFilter(context.getColor(R.color.colorGray));
        holder.happy_face.setColorFilter(context.getColor(R.color.colorGray));
        holder.veryhappy_face.setColorFilter(context.getColor(R.color.colorGray));
    }

    @Override
    public int getItemCount() {
        return questionTitles.size();
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout parentLayout;
        TextView questionTitle;
        TextView question;
        ImageView verysad_face;
        ImageView sad_face;
        ImageView base_face;
        ImageView happy_face;
        ImageView veryhappy_face;

        public SurveyViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.survey_item_parent_layout);
            questionTitle = itemView.findViewById(R.id.survey_item_question_title);
            question = itemView.findViewById(R.id.survey_item_question);

            verysad_face = itemView.findViewById(R.id.survey_verysad_face);
            sad_face = itemView.findViewById(R.id.survey_sad_face);
            base_face = itemView.findViewById(R.id.survey_base_face);
            happy_face = itemView.findViewById(R.id.survey_happy_face);
            veryhappy_face = itemView.findViewById(R.id.survey_veryhappy_face);
        }
    }
}
