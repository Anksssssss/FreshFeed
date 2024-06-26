package com.example.freshfeed.repo


import com.example.freshfeed.BuildConfig
import com.example.freshfeed.api.NewsApi
import com.example.freshfeed.api.Resource
import com.example.freshfeed.db.NewsDatabase
import com.example.freshfeed.models.Article
import com.example.freshfeed.models.SavedArticles
import com.example.freshfeed.models.Summary
import com.example.freshfeed.models.TopHeadlines


class Repository(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase,
    private val summaryApi: NewsApi
):BaseRepo() {

    suspend fun getTopHeadlines(
        country: String,
        category: String
    ): Resource<TopHeadlines> {
        return safeApiCall { newsApi.getTopHeadlines(country,category,BuildConfig.apiKey) }
    }

    suspend fun insertArticles(articles: List<Article>) {
        newsDatabase.newsDao().insert(articles)
    }

    fun getAllArticles(category:String): List<Article> {
        return newsDatabase.newsDao().getNewsArticles(category)
    }

    fun deleteAllArticles(category:String){
        newsDatabase.newsDao().deleteAll(category)
    }

    suspend fun getSearchedNews(
        query: String
    ): Resource<TopHeadlines> {
        return safeApiCall { newsApi.getSearchedNews(query,BuildConfig.apiKey) }
    }
    suspend fun insertSavedArticle(article:SavedArticles){
        newsDatabase.newsDao().insertSavedNews(article)
    }

    suspend fun getAllSavedArticles():List<SavedArticles>{
        return newsDatabase.newsDao().getAllSavedArticles()
    }

    fun deleteSavedArticle(article: SavedArticles){
        newsDatabase.newsDao().deleteFromSavedNews(article)
    }

    suspend fun getSummary(
        url: String,
        sentences: Int
    ): Resource<Summary> {
        return safeApiCall { summaryApi.getSummary(BuildConfig.summaryKey,url,sentences) }
    }

}