package com.example.newspaper;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_article_detail);

        ImageView thumbnail = findViewById(R.id.thumbnail);
        ImageView avatar = findViewById(R.id.avatar);

        Glide.with(this)
                .load("https://images2.thanhnien.vn/528068263637045248/2025/5/3/edit-3-1746246222496141030607.jpeg")
                .into(thumbnail);

        Glide.with(this)
                .load("https://cdnphoto.dantri.com.vn/dpUx3g4l-67rPhQqukF9fel8sn0=/zoom/120_120/2023/06/14/anh-1-crop-1686754741433.jpeg")
                .apply(RequestOptions.circleCropTransform())
                .into(avatar);

        findViewById(R.id.back_icon).setOnClickListener(v -> {
            finish();
        });
    }
}