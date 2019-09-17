package com.aidawhale.tfmarcore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    // Recycler view used on MainActivity to populate user list

    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<String> mUserNames;
    private ArrayList<String> mUserColors;


    public RecyclerViewAdapter(Context context, ArrayList<String> userNames, ArrayList<String> userColors) {
        this.mContext = context;
        this.mUserNames = userNames;
        this.mUserColors = userColors;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {
        // called once per list item when they are created

        holder.userName.setText(mUserNames.get(position));

        if (position == mUserNames.size()-1) {
            holder.userColor.setColorFilter(R.color.colorPrimaryDark);
        } else {
            holder.userColor.setColorFilter(R.color.colorAccent);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked.");

                if (position == mUserNames.size()-1) {
                    Toast.makeText(mContext, "Adding new user", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, mUserNames.get(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, UserMenuActivity.class);
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        ImageView userColor;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userColor = itemView.findViewById(R.id.user_color);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
