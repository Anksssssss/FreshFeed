package com.example.freshfeed.api

import com.example.freshfeed.models.TopHeadlines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
    ): Response<TopHeadlines>
}