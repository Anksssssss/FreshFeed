package com.example.freshfeed.utils

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.freshfeed.models.Article
import com.example.freshfeed.models.NewsArticles
import com.example.freshfeed.repo.Repository
import com.example.freshfeed.viewModels.NewsViewModel

object MyUtils {
    fun filterArticlesByCategory(dbArticles: List<NewsArticles>, category: String): List<Article> {
        val tempList = mutableListOf<Article>()
        for (article in dbArticles) {
            if (article.category == category) {
                val temp = Article(
                    article.id,
                    article.source,
                    article.author,
                    article.title,
                    article.description,
                    article.url,
                    article.urlToImage,
                    article.publishedAt,
                    article.content
                )
                tempList.add(temp)
            }
        }
        return tempList
    }
}