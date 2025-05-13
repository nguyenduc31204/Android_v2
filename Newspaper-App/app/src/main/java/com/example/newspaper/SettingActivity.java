package com.example.newspaper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newspaper.ui.settings.AdsContactSetting;
import com.example.newspaper.ui.settings.FeedbackSetting;
import com.example.newspaper.ui.settings.NotificationSetting;
import com.example.newspaper.ui.settings.RatingSettingActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private TextView textSizeStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        findViewById(R.id.back_icon).setOnClickListener(v -> {
            finish();
        });

        prefs = getSharedPreferences("settings", MODE_PRIVATE);

        navigate(R.id.notification_icon, NotificationSetting.class);

        // DARK MODE SETTINGS
        View darkModeSetting = findViewById(R.id.dark_mode_icon);
        ViewGroup parent = (ViewGroup) darkModeSetting.getParent();
        TextView darkModeStatus = parent.findViewById(R.id.dark_mode_status);

        int savedMode = prefs.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(savedMode);
        updateDarkModeStatusText(savedMode, darkModeStatus);

        darkModeSetting.setOnClickListener(v -> {
            View view = getLayoutInflater().inflate(R.layout.setting_dark_mode, null);
            BottomSheetDialog dialog = new BottomSheetDialog(SettingActivity.this);
            dialog.setContentView(view);

            TextView off = findViewById(R.id.option_off);
            TextView on = findViewById(R.id.option_on);
            TextView system = findViewById(R.id.option_system);
            TextView cancel = findViewById(R.id.cancel_button);

            View.OnClickListener listener = v1 -> {
                int mode;
                if (v1 == off) {
                    mode = AppCompatDelegate.MODE_NIGHT_NO;
                } else if (v1 == on) {
                    mode = AppCompatDelegate.MODE_NIGHT_YES;
                } else {
                    mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                }

                AppCompatDelegate.setDefaultNightMode(mode);
                prefs.edit().putInt("night_mode", mode).apply();
                updateDarkModeStatusText(mode, darkModeStatus);
                recreate();
                dialog.dismiss();
            };

            off.setOnClickListener(listener);
            on.setOnClickListener(listener);
            system.setOnClickListener(listener);
            cancel.setOnClickListener(v1 -> dialog.dismiss());

            dialog.show();
        });

        // TEXT SIZE SETTINGS
        ImageView nut4 = findViewById(R.id.nut4);
        parent = (ViewGroup) nut4.getParent();
        textSizeStatus = parent.findViewById(R.id.text_size_status);

        // Cập nhật hiển thị kích thước chữ ban đầu
        float savedScale = prefs.getFloat("text_scale", 1.0f);
        updateTextSizeStatus(savedScale, textSizeStatus);

        nut4.setOnClickListener(v -> {
            View view = getLayoutInflater().inflate(R.layout.setting_text_size, null);
            BottomSheetDialog dialog = new BottomSheetDialog(SettingActivity.this);
            dialog.setContentView(view);

            TextView normal = findViewById(R.id.option_normal);
            TextView large = findViewById(R.id.option_large);
            TextView extraLarge = findViewById(R.id.option_extra_large);
            TextView cancel = findViewById(R.id.btn_cancel);

            View.OnClickListener listener = v1 -> {
                float scale;
                if (v1 == normal) scale = 1.0f;
                else if (v1 == large) scale = 1.15f;
                else scale = 1.3f;

                prefs.edit().putFloat("text_scale", scale).apply();
                updateTextSizeStatus(scale, textSizeStatus);
                dialog.dismiss();
            };

            normal.setOnClickListener(listener);
            large.setOnClickListener(listener);
            extraLarge.setOnClickListener(listener);
            cancel.setOnClickListener(v1 -> dialog.dismiss());

            dialog.show();
        });
        navigate(R.id.feedback_icon, FeedbackSetting.class);
//        ImageView infoIcon = findViewById(R.id.nut6);
//        infoIcon.setOnClickListener(v -> {
//            Intent intent = new Intent(SettingActivity.this, EditorialInfoActivity.class);
//            startActivity(intent);
//        });

        navigate(R.id.ads_contact_icon, AdsContactSetting.class);
        findViewById(R.id.rating_icon).setOnClickListener(v -> showRatingDialog());

    }

    public void navigate(int viewId, Class<?> targetActivity) {
        View button = findViewById(viewId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, targetActivity);
            startActivity(intent);
        });
    }

    private void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.setting_rating, null);
        builder.setView(dialogView);

        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        TextView btnLater = dialogView.findViewById(R.id.btn_later);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            dialog.dismiss();
            // Nếu rating >= 4, mở link Play Store
            if (rating >= 4) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        btnLater.setOnClickListener(v -> dialog.dismiss());
    }


    private void updateDarkModeStatusText(int mode, TextView statusTextView) {
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                statusTextView.setText("Tắt");
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                statusTextView.setText("Bật");
                break;
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
            default:
                statusTextView.setText("Hệ thống");
                break;
        }
    }

    private void updateTextSizeStatus(float scale, TextView statusTextView) {
        if (scale == 1.0f) {
            statusTextView.setText("Bình thường");
        } else if (scale == 1.15f) {
            statusTextView.setText("Lớn");
        } else {
            statusTextView.setText("Rất lớn");
        }

        statusTextView.setTextSize(16 * scale);
    }
}