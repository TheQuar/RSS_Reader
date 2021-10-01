package com.quar.taskd2.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.quar.taskd2.room.BreakNewsTable;
import com.quar.taskd2.room.TopNewsTable;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);
    }

    public LiveData<PagedList<BreakNewsTable>> getBreakNews() {
        return newsRepository.getBreakNews();
    }

    public void insertBreakNews(List<BreakNewsTable> breakNewsTable) {
        newsRepository.insertBreakNews(breakNewsTable);
    }

    public LiveData<PagedList<TopNewsTable>> getTopNews() {
        return newsRepository.getTopNews();
    }

    public void insertTopNews(List<TopNewsTable> topNewsTables) {
        newsRepository.insertTopNews(topNewsTables);
    }
    public void deleteNews() {
        newsRepository.deleteNews();
    }


}
