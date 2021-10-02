package com.quar.taskd2.room;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.quar.taskd2.models.NewsModel;

import java.util.List;


@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBreakNews(List<BreakNewsTable> breakNewsTable);

    @Query("SELECT * FROM BreakNewsTable")
    DataSource.Factory<Integer, NewsModel> getBreakNews();

    @Query(" Delete FROM BreakNewsTable ")
    void deleteBreakNewsTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopNews(List<TopNewsTable> topNewsTables);

    @Query("SELECT * FROM TopNewsTable")
    DataSource.Factory<Integer, NewsModel> getTopNews();

    @Query(" Delete FROM TopNewsTable")
    void deleteTopNewsTable();
}
