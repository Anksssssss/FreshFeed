package com.example.freshfeed.repo


import com.example.freshfeed.BuildConfig
import com.example.freshfeed.api.NewsApi
import com.example.freshfeed.api.Resource
import com.example.freshfeed.db.NewsDatabase
import com.example.freshfeed.models.Article
import com.example.freshfeed.models.NewsArticles
import com.example.freshfeed.models.TopHeadlines


class Repository(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase
):BaseRepo() {

    suspend fun getTopHeadlines(
        country: String,
        category: String
    ): Resource<TopHeadlines> {
        return safeApiCall { newsApi.getTopHeadlines(country,category,BuildConfig.apiKey) }
    }

    suspend fun insertArticles(article: NewsArticles) {
        newsDatabase.newsDao().insert(article)
    }

    fun getAllArticles(): List<NewsArticles> {
        return newsDatabase.newsDao().getNewsArticles()
    }

    fun deleteAllArticles(category:String){
        newsDatabase.newsDao().deleteAll(category)
    }
}