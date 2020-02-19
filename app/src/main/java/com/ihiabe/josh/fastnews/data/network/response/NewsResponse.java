package com.ihiabe.josh.fastnews.data.network.response;

import com.ihiabe.josh.fastnews.data.database.entity.Articles;

import java.util.List;

public class NewsResponse {
    private String status;
    private int totalResults;
    private List<Articles> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }
}
