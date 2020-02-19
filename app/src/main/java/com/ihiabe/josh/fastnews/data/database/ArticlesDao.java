package com.ihiabe.josh.fastnews.data.database;

import com.ihiabe.josh.fastnews.data.database.entity.Articles;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsertArticles(Articles articles);

    @Query("select * from articles_table order by publishedAt desc")
    LiveData<List<Articles>> getAllArticles();
}
