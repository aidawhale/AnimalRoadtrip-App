package com.aidawhale.tfmarcore.ui.selectgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.aidawhale.tfmarcore.R;
import com.aidawhale.tfmarcore.UserMenuActivity;

public class SelectGameFragment extends Fragment {

    private SelectGameViewModel selectGameViewModel;
    private String userID = "dumb";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        selectGameViewModel =
                ViewModelProviders.of(this).get(SelectGameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_selectgame, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        CardView cv1 = getView().findViewById(R.id.first_game_cardview);
        CardView cv2 = getView().findViewById(R.id.second_game_cardview);
        CardView cv3 = getView().findViewById(R.id.third_game_cardview);

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Starting first game...", Toast.LENGTH_SHORT).show();
                // Init game with corresponding UnityActivity
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Starting second game...", Toast.LENGTH_SHORT).show();
                // Init game with corresponding UnityActivity
            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Starting third game...", Toast.LENGTH_SHORT).show();
                // Init game with corresponding UnityActivity
            }
        });

        userID = UserMenuActivity.getUserID();

        TextView gridHeader = getView().findViewById(R.id.grid_header);
        if(userID != null) {
            gridHeader.setText("Login " + userID);
        } else {
            gridHeader.setText("Login without QR");
        }
    }

}