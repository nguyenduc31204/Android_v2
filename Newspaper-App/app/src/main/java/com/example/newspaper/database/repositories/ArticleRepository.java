package com.example.newspaper.database.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagingSource;

import com.example.newspaper.database.DatabaseHandler;
import com.example.newspaper.database.dao.ArticleDao;
import com.example.newspaper.models.Article;

import java.util.List;
import java.util.concurrent.Executors;

public class ArticleRepository{
    private ArticleDao articleDao;

    private LiveData<List<Article>> articles;

    private PagingSource<Integer, Article> pageArticles;

    public ArticleRepository(Context context){
        DatabaseHandler dbh = DatabaseHandler.getInstance(context);

        this.articleDao = dbh.getArticleDao();
        this.articles = articleDao.findAlls();
//        this.pageArticles = articleDao.getPageArticle();
    }

    public void insert(Article article) {
        Executors.newSingleThreadExecutor().execute(() -> {
            this.articleDao.insert(article);
        });
    }

    public void update(Article article) {
        Executors.newSingleThreadExecutor().execute(() -> {
            this.articleDao.update(article);
        });
    }

    public void delete(Article article) {
        Executors.newSingleThreadExecutor().execute(() -> {
            this.articleDao.delete(article);
        });
    }

    public void deleteAlls() {
        Executors.newSingleThreadExecutor().execute(() -> {
            this.articleDao.deleteAlls();
        });
    }

    public LiveData<List<Article>> findAlls() {
        return this.articles;
    }

    public PagingSource<Integer, Article> getPageArticle() {
        return this.pageArticles;
    }
}
