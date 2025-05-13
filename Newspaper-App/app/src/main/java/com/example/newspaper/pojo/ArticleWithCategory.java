package com.example.newspaper.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.newspaper.models.Article;
import com.example.newspaper.models.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleWithCategory {
    @Embedded
    public Article article;

    @Relation(
            parentColumn = "category_id",
            entityColumn = "id"
    )
    public Category category;
}
