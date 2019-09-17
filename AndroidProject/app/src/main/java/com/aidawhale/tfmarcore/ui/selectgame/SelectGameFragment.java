package com.aidawhale.tfmarcore.ui.selectgame;

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

public class SelectGameFragment extends Fragment {

    private SelectGameViewModel selectGameViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        selectGameViewModel =
                ViewModelProviders.of(this).get(SelectGameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_selectgame, container, false);
        final TextView textView = root.findViewById(R.id.text_selectgame);
        selectGameViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}