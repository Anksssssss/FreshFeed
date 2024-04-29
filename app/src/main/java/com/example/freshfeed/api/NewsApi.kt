package com.example.freshfeed.api

import com.example.freshfeed.models.Summary
import com.example.freshfeed.models.TopHeadlines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
    ): Response<TopHeadlines>

    @GET("top-headlines")
    suspend fun getSearchedNews(
        @Query("q") q:String,
        @Query("apiKey") apiKey: String,
    ): Response<TopHeadlines>

    @POST("summarization-1.0")
    suspend fun getSummary(
        @Query("key") key: String,
        @Query("url") url: String,
        @Query("sentences") sentences:Int
    ): Response<Summary>
}