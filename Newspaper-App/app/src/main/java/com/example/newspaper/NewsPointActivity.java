package com.example.newspaper;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;

public class NewsPointActivity extends AppCompatActivity {
    TextView title, childTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_point);

        findViewById(R.id.back_icon).setOnClickListener(v -> {
            finish();
        });
        mapping();

        String type = getIntent().getStringExtra("type");
        System.out.println(type);
        assert type != null;
        if(type.equals("newest")){
            title.setText("Tin mới nhất");
            childTitle.setText(String.format("TIN MỚI NHẤT TRONG NGÀY %s", LocalDate.now()));
        }else if(type.equals("new-point")){
            title.setText("Điểm tin");
            childTitle.setText(String.format("ĐIỂM TIN NỔI BẬT HÔM QUA %s", LocalDate.now().minusDays(1)));
        }
    }

    public void mapping(){
        title = findViewById(R.id.title);
        childTitle = findViewById(R.id.childTitle);
    }
}