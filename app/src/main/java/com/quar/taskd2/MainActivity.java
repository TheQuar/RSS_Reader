package com.quar.taskd2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import com.quar.taskd2.adapter.AdapterBreakingNews;
import com.quar.taskd2.adapter.AdapterTopNews;
import com.quar.taskd2.config.AppConfig;
import com.quar.taskd2.livedata.NewsViewModel;
import com.quar.taskd2.models.CallBackNews;
import com.quar.taskd2.models.NewsDetails;
import com.quar.taskd2.rests.RestAdapter;
import com.quar.taskd2.room.BreakNewsTable;
import com.quar.taskd2.room.TopNewsTable;
import com.quar.taskd2.ui.UIConfig;
import com.quar.taskd2.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.http2.Http2Reader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private NewsViewModel newsViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AdapterBreakingNews adapterBreakingNews;
    private AdapterTopNews adapterTopNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIConfig.change_status_bar_text(this);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        swipeRefreshLayout = findViewById(R.id.swip_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView break_news_rv = findViewById(R.id.rv_news);
        break_news_rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapterBreakingNews = new AdapterBreakingNews(this);
        break_news_rv.setAdapter(adapterBreakingNews);

        RecyclerView top_news_rv = findViewById(R.id.rv_top_news);
        top_news_rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapterTopNews = new AdapterTopNews(this);
        top_news_rv.setAdapter(adapterTopNews);

        check_network();

    }

    //check
    private void check_network() {
        swipeRefreshLayout.setRefreshing(true);
        if (Utils.check_network(MainActivity.this)) {
            newsViewModel.deleteNews();
            getNews();
        } else {
            alertDialog("Internet mavjud emas siz offline ma'lumotdan foydalanyapsiz");
            swipeRefreshLayout.setRefreshing(false);
        }

        getDataFromDb();

    }

    private void getDataFromDb() {
        newsViewModel.getBreakNews().observe(this, breakNewsTables -> {
            if (breakNewsTables != null) {
                adapterBreakingNews.submitList(breakNewsTables);

            }
        });

        newsViewModel.getTopNews().observe(this, topNewsTables -> {
            if (topNewsTables != null) {
                adapterTopNews.submitList(topNewsTables);
            }
        });
    }

    private void getNews() {
        RestAdapter.createAPI().getTopBusinessNews("us", "business", AppConfig.API_KEY)
                .enqueue(new Callback<CallBackNews>() {
                    @Override
                    public void onResponse(Call<CallBackNews> call, Response<CallBackNews> response) {
                        if (response.isSuccessful() && response.body().getStatus().equals("ok")) {


                            List<BreakNewsTable> breakNewsTables = new ArrayList<>();

                            for (NewsDetails ns : response.body().getArticles()) {
                                breakNewsTables.add(new BreakNewsTable(
                                        ns.getAuthor(),
                                        ns.getTitle(),
                                        ns.getDescription(),
                                        ns.getUrl(),
                                        ns.getUrlToImage(),
                                        ns.getPublishedAt(),
                                        ns.getContent(),
                                        ns.getSource().get("name").toString()
                                ));
                            }
                            newsViewModel.insertBreakNews(breakNewsTables);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            isError();
                        }
                    }

                    @Override
                    public void onFailure(Call<CallBackNews> call, Throwable t) {
                        isError();
                    }
                });

        RestAdapter.createAPI().getTechCrunchNews("techcrunch", AppConfig.API_KEY)
                .enqueue(new Callback<CallBackNews>() {
                    @Override
                    public void onResponse(Call<CallBackNews> call, Response<CallBackNews> response) {
                        if (response.isSuccessful() && response.body().getStatus().equals("ok")) {

                            swipeRefreshLayout.setRefreshing(true);
                            List<TopNewsTable> topNewsTables = new ArrayList<>();
                            for (NewsDetails ns : response.body().getArticles()) {
                                topNewsTables.add(new TopNewsTable(
                                        ns.getAuthor(),
                                        ns.getTitle(),
                                        ns.getDescription(),
                                        ns.getUrl(),
                                        ns.getUrlToImage(),
                                        ns.getPublishedAt(),
                                        ns.getContent(),
                                        ns.getSource().get("name").toString()
                                ));
                            }
                            newsViewModel.insertTopNews(topNewsTables);

                            swipeRefreshLayout.setRefreshing(false);

                        } else {
                            isError();
                        }
                    }

                    @Override
                    public void onFailure(Call<CallBackNews> call, Throwable t) {
                        isError();
                    }
                });

    }


    @Override
    public void onRefresh() {
        check_network();
    }


    private void isError() {
        alertDialog("yangiliklar olishda xatolik qayta urinib ko'ring");
        swipeRefreshLayout.setRefreshing(false);

    }

    private void alertDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

}