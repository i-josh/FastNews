package com.ihiabe.josh.fastnews.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ihiabe.josh.fastnews.R;
import com.ihiabe.josh.fastnews.data.database.entity.Articles;
import com.ihiabe.josh.fastnews.ui.adapter.NewsRecyclerAdapter;

import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private NewsRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mAdapter = new NewsRecyclerAdapter(this);

        NewsViewModel viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        viewModel.getAllArticles().observe(this, new Observer<List<Articles>>() {
            @Override
            public void onChanged(List<Articles> articles) {
                mAdapter.setData(articles);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NewsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Articles articles) {
                Intent intent = new Intent(NewsActivity.this,NewsDetailActivity.class);
                intent.putExtra("extra_title",articles.getTitle());
                intent.putExtra("extra_author",articles.getAuthor());
                intent.putExtra("extra_description",articles.getDescription());
                intent.putExtra("extra_image",articles.getUrlToImage());
                intent.putExtra("extra_url",articles.getUrl());
                startActivity(intent);

            }
        });

    }
}
