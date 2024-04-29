package com.example.freshfeed

import android.app.Application
import com.example.freshfeed.api.NewsApiService
import com.example.freshfeed.api.SummarizationService
import com.example.freshfeed.db.NewsDatabase
import com.example.freshfeed.repo.Repository

class MyApplication: Application() {
    lateinit var newsRepository:Repository
    override fun onCreate() {
        super.onCreate()

        val newsApi = NewsApiService.api
        val summaryApi = SummarizationService.api
        val database = NewsDatabase.getInstance(applicationContext)
        newsRepository = Repository(newsApi,database,summaryApi)
    }
}