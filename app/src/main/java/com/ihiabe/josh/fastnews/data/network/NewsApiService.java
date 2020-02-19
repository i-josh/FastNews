package com.ihiabe.josh.fastnews.data.network;

import com.ihiabe.josh.fastnews.data.network.response.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    @GET("top-headlines")
    Call<NewsResponse> getNews(
         @Query("country") String country,
         @Query("apiKey") String key
    );
}