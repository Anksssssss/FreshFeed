package com.example.freshfeed.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiService {

    private val BASE_URL = "https://newsapi.org/v2/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)

}