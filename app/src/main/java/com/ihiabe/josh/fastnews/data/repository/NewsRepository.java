package com.ihiabe.josh.fastnews.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.ihiabe.josh.fastnews.data.database.ArticlesDao;
import com.ihiabe.josh.fastnews.data.database.NewsDatabase;
import com.ihiabe.josh.fastnews.data.database.entity.Articles;
import com.ihiabe.josh.fastnews.data.network.NewsApiService;
import com.ihiabe.josh.fastnews.data.network.response.NewsResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRepository {

    private ArticlesDao mArticlesDao;
    private LiveData<List<Articles>> allArticles;
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static final String API_KEY = "7632ef2bf27146068b40dec63660adf6";

    public NewsRepository(Application application){
        NewsDatabase database = NewsDatabase.getInstance(application);
        mArticlesDao = database.articlesDao();
        allArticles = mArticlesDao.getAllArticles();

        persistFetchedData();
    }

    private void persistFetchedData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApiService apiService = retrofit.create(NewsApiService.class);
        Call<NewsResponse> callService = apiService.getNews("us",API_KEY);
        callService.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()){
                    NewsResponse newsResponse = response.body();
                    List<Articles> articles = newsResponse.getArticles();
                    for (Articles art : articles){
                        new FetchDataTask(mArticlesDao).execute(art);
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Articles>> getAllArticles(){
        return allArticles;
    }

    public static class FetchDataTask extends AsyncTask<Articles,Void,Void>{

        private ArticlesDao articleDao;

        private FetchDataTask(ArticlesDao articleDao){
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Articles... articles) {
            articleDao.upsertArticles(articles[0]);
            return null;
        }
    }
}
