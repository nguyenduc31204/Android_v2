package com.example.newspaper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newspaper.models.Article;

import java.util.List;


@Dao
public interface ArticleDao {

    @Insert
    void insert(Article article);

    @Update
    void update(Article article);

    @Delete
    void delete(Article article);

    @Query("DELETE FROM article_table")
    void deleteAlls();

    @Query("SELECT * FROM article_table ORDER BY publishedAt ASC")
    LiveData<List<Article>> findAlls();

//    @Query("SELECT * FROM article_table ORDER BY publishedAt ASC")
//    PagingSource<Integer, Article> getPageArticle();
}
