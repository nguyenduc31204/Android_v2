package com.example.newspaper.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newspaper.HistoryActivity;
import com.example.newspaper.NotificationActivity;
import com.example.newspaper.R;
import com.example.newspaper.SettingActivity;

public class FragmentUtil extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_util, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigate(view, R.id.setting_icon, SettingActivity.class);
        navigate(view, R.id.notification_icon, NotificationActivity.class);

        view.findViewById(R.id.saved_icon).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), HistoryActivity.class);
            intent.putExtra("type", "saved");
            startActivity(intent);
        });
        view.findViewById(R.id.history_icon).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), HistoryActivity.class);
            intent.putExtra("type", "history");
            startActivity(intent);
        });
    }

    public void navigate(View parentView, int viewId, Class<?> targetActivity) {
        View button = parentView.findViewById(viewId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), targetActivity);
            startActivity(intent);
        });
    }
}
