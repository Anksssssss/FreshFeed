package com.example.freshfeed.db


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.freshfeed.models.Article
import com.example.freshfeed.models.SavedArticles

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles:List<Article>)

    @Query("DELETE FROM NewsArticle WHERE category = :category")
    fun deleteAll(category: String)

    @Query("SELECT * FROM NewsArticle WHERE category = :category")
    fun getNewsArticles(category: String): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedNews(article: SavedArticles)

    @Query("SELECT * FROM SavedArticles")
    fun getAllSavedArticles(): List<SavedArticles>

    @Delete
    fun deleteFromSavedNews(article:SavedArticles)

}