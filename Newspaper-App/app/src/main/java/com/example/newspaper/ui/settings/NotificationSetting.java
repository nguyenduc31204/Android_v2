package com.example.newspaper.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.newspaper.R;

public class NotificationSetting extends AppCompatActivity {

    private static final String PREF_NAME = "AppSettings";
    private static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";

    private SwitchCompat notificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_notification);

        notificationSwitch = findViewById(R.id.switch_notifications);

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isEnabled = sharedPreferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
        notificationSwitch.setChecked(isEnabled);

        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit()
                    .putBoolean(KEY_NOTIFICATIONS_ENABLED, isChecked)
                    .apply();
        });

        findViewById(R.id.back_icon).setOnClickListener(v -> {
            finish();
        });
    }
}
