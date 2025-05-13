package com.example.newspaper.ui.adapters.view_holders;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.ui.adapters.view_items.ArticleViewItem;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(T item);

    public <T extends View> T findViewById(@IdRes int id){
        return this.itemView.findViewById(id);
    }
}
