package com.example.newspaper.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.R;

public class FeedbackSetting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_feedback);

        findViewById(R.id.back_icon).setOnClickListener(v -> {
            finish();
        });
    }
}
