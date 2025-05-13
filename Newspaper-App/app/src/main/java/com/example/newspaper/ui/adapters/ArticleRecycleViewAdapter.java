package com.example.newspaper.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.newspaper.R;
import com.example.newspaper.models.Article;
import com.example.newspaper.ui.adapters.view_holders.ArticleCategoryViewHolder;
import com.example.newspaper.ui.adapters.view_holders.ArticleViewHolder;
import com.example.newspaper.ui.adapters.view_holders.BaseViewHolder;
import com.example.newspaper.ui.adapters.view_holders.NotificationHolder;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;

import java.util.List;

public class ArticleRecycleViewAdapter extends BaseRecycleViewAdapter {
    private List<ArticleViewItem> items;

    public ArticleRecycleViewAdapter(List<ArticleViewItem> items) {
        super(items);
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType().ordinal();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ArticleViewItem.TypeDisplay type = ArticleViewItem.TypeDisplay.values()[viewType];

        switch (type) {
            case MAIN:
                return new ArticleViewHolder(inflater.inflate(R.layout.item_blog_view, parent, false));
            case CATEGORY:
                return new ArticleCategoryViewHolder(inflater.inflate(R.layout.item_article_category_view, parent, false));
            case NOTIFICATION:
                return new NotificationHolder(inflater.inflate(R.layout.item_notification_view, parent, false));
            default:
                throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
}
