package com.quar.rss.livedata;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.quar.rss.models.NewsModel;
import com.quar.rss.room.AppDatabase;
import com.quar.rss.room.BreakNewsTable;
import com.quar.rss.room.NewsDao;
import com.quar.rss.room.TopNewsTable;

import java.util.List;

public class NewsRepository extends AndroidViewModel {

    private static final int PAGE_SIZE = 10;

    private AppDatabase appDatabase;

    public NewsRepository(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());

    }

    public LiveData<PagedList<NewsModel>> getBreakNews() {
        return new LivePagedListBuilder(appDatabase.myQuery().getBreakNews(), PAGE_SIZE).build();
    }


    public void insertBreakNews(List<BreakNewsTable> breakNewsTable) {
        new InsertBreakAsyncTask(appDatabase.myQuery()).execute(breakNewsTable);
    }


    public LiveData<PagedList<NewsModel>> getTopNews() {
        return new LivePagedListBuilder(appDatabase.myQuery().getTopNews(), PAGE_SIZE).build();
    }


    public void insertTopNews(List<TopNewsTable> topNewsTables) {
        new InsertTopAsyncTask(appDatabase.myQuery()).execute(topNewsTables);
    }


    public void deleteNews() {
        appDatabase.myQuery().deleteBreakNewsTable();
        appDatabase.myQuery().deleteTopNewsTable();

    }



    private static class InsertBreakAsyncTask extends AsyncTask<List<BreakNewsTable>, Void, Void> {

        private NewsDao newsDao;

        public InsertBreakAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(List<BreakNewsTable>... lists) {
            newsDao.insertBreakNews(lists[0]);
            return null;
        }
    }


    private static class InsertTopAsyncTask extends AsyncTask<List<TopNewsTable>, Void, Void> {

        private NewsDao newsDao;

        public InsertTopAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(List<TopNewsTable>... lists) {
            newsDao.insertTopNews(lists[0]);
            return null;
        }
    }

}
