package com.example.newspaper.ui.adapters.view_holders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newspaper.ArticleDetailActivity;
import com.example.newspaper.R;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;
import com.example.newspaper.utils.DateTimeFormatterUtil;

public class NotificationHolder extends BaseViewHolder<ArticleViewItem>{

    ImageView thumbnail;
    TextView title, publishedAt;
    View read;
    public NotificationHolder(@NonNull View itemView) {
        super(itemView);

        thumbnail = findViewById(R.id.thumbnail);
        title = findViewById(R.id.title);
        publishedAt = findViewById(R.id.publish);
        read = findViewById(R.id.read);

        itemView.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(itemView.getContext(), ArticleDetailActivity.class);
                itemView.setAlpha(0.5f);
                read.setAlpha(0);
                itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolder(ArticleViewItem item) {
        DateTimeFormatterUtil formatter = new DateTimeFormatterUtil();

        publishedAt.setText(formatter.format(item.getArticle().article.getPublishedAt()));
        title.setText(item.getArticle().article.getTitle());
        Glide.with(itemView.getContext())
                .load(item.getArticle().article.getThumbnailUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(thumbnail);

    }
}
