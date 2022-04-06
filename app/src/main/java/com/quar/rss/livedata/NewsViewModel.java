package com.quar.rss.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.quar.rss.models.NewsModel;
import com.quar.rss.room.BreakNewsTable;
import com.quar.rss.room.TopNewsTable;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);
    }

    public LiveData<PagedList<NewsModel>> getBreakNews() {
        return newsRepository.getBreakNews();
    }

    public void insertBreakNews(List<BreakNewsTable> breakNewsTable) {
        newsRepository.insertBreakNews(breakNewsTable);
    }

    public LiveData<PagedList<NewsModel>> getTopNews() {
        return newsRepository.getTopNews();
    }

    public void insertTopNews(List<TopNewsTable> topNewsTables) {
        newsRepository.insertTopNews(topNewsTables);
    }
    public void deleteNews() {
        newsRepository.deleteNews();
    }


}
