package com.example.newspaper.ui.adapters.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newspaper.database.repositories.ArticleRepository;
import com.example.newspaper.models.Article;

import java.util.List;

import lombok.Getter;

public class ArticleViewModel extends AndroidViewModel {
    private ArticleRepository repository;

    @Getter
    private LiveData<List<Article>> articles;
    public ArticleViewModel(@NonNull Application application) {
        super(application);

        this.repository = new ArticleRepository(application.getApplicationContext());
        this.articles = repository.findAlls();
    }

}
