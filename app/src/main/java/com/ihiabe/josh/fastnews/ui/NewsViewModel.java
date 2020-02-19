package com.ihiabe.josh.fastnews.ui;


import android.app.Application;

import com.ihiabe.josh.fastnews.data.database.entity.Articles;
import com.ihiabe.josh.fastnews.data.repository.NewsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends AndroidViewModel {

    private LiveData<List<Articles>> allArticles;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        NewsRepository repository = new NewsRepository(application);
        allArticles = repository.getAllArticles();
    }

    public LiveData<List<Articles>> getAllArticles(){
        return allArticles;
    }
}
