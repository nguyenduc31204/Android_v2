package com.example.newspaper.ui.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.ui.adapters.view_holders.BaseViewHolder;
import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;

import java.util.List;

public class BaseRecycleViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ArticleViewItem> items;

    public BaseRecycleViewAdapter(List<ArticleViewItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(this.items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
