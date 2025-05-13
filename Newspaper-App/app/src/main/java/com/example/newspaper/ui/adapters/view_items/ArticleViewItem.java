package com.example.newspaper.ui.adapters.view_items;

import com.example.newspaper.models.Article;
import com.example.newspaper.pojo.ArticleWithCategory;

import java.util.List;

import lombok.Getter;

@Getter
public class ArticleViewItem {

    public ArticleViewItem(ArticleWithCategory article, TypeDisplay typeDisplay) {
        this.article = article;
        this.type = typeDisplay;
    }

    public enum TypeDisplay{
        MAIN,
        CATEGORY,
        NOTIFICATION
    }
    private ArticleWithCategory article;
    private TypeDisplay type;
}
