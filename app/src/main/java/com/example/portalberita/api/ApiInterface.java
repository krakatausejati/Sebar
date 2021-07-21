package com.example.portalberita.api;

import com.example.portalberita.entity.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/v2/top-headlines")
    Call<News> getListNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

    //Semua Berita
    @GET("/v2/top-headlines")
    Call<News> getListAllNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    //Untuk Pencarian
    @GET("/v2/everything")
    Call<News> getNewsSearch(
            @Query("q") String keyword,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
}
