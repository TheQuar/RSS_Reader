package com.quar.taskd2.rests;

import com.quar.taskd2.models.CallBackNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    //TechCrunch
    @GET("v2/top-headlines")
    Call<CallBackNews> getTechCrunchNews(@Query("sources") String techcrunch, @Query("apiKey") String apiKey);

    //Top Business
    @GET("v2/top-headlines")
    Call<CallBackNews> getTopBusinessNews(@Query("country") String country, @Query("category") String category ,@Query("apiKey") String apiKey);

}
