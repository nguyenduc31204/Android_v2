package com.example.newspaper.ui.adapters.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.newspaper.R;
import com.example.newspaper.models.Article;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;
import com.example.newspaper.utils.DateTimeFormatterUtil;

public class ArticleCategoryViewHolder extends BaseViewHolder<ArticleViewItem>{
    private TextView timeText;
    private TextView categoryText;
    private TextView titleText;
    private TextView descriptionText;
    private View categoryDot;

    public ArticleCategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        timeText = itemView.findViewById(R.id.timeText);
        categoryText = itemView.findViewById(R.id.categoryText);
        titleText = itemView.findViewById(R.id.titleText);
        descriptionText = itemView.findViewById(R.id.descriptionText);
        categoryDot = itemView.findViewById(R.id.categoryDot);
    }

    @Override
    public void onBindViewHolder(ArticleViewItem item) {
        DateTimeFormatterUtil formatter = new DateTimeFormatterUtil();

        timeText.setText(formatter.format(item.getArticle().article.getPublishedAt()));
        categoryText.setText(item.getArticle().category.getName());
        titleText.setText(item.getArticle().article.getTitle());
        descriptionText.setText(item.getArticle().article.getSummary());
        categoryDot.setBackgroundResource(R.drawable.circle_green);
    }
}
