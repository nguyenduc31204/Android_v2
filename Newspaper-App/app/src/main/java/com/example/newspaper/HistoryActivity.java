package com.example.newspaper;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        findViewById(R.id.back_icon).setOnClickListener(v -> {
            finish();
        });

        TextView title = findViewById(R.id.title);
        String type = getIntent().getStringExtra("type");
        if(type.equals("history")) title.setText("Bài viết đã xem");
        else if(type.equals("saved")) title.setText("Bài viết đã lưu");
    }
}