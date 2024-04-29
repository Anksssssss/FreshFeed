package com.example.freshfeed.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SummarizationService {

    private val BASE_URL = "https://api.meaningcloud.com/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)

}