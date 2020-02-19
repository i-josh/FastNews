package com.ihiabe.josh.fastnews.data.database;

import android.content.Context;

import com.ihiabe.josh.fastnews.data.database.entity.Articles;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Articles.class},version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    private static NewsDatabase instance;
    public abstract ArticlesDao articlesDao();

    public static NewsDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NewsDatabase.class,"news_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
