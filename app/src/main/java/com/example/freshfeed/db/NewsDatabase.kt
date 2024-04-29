package com.example.freshfeed.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.freshfeed.models.Article
import com.example.freshfeed.models.SavedArticles

@Database(entities = [Article::class,SavedArticles::class], version = 8, exportSchema = false)
@TypeConverters(SourceTypeConverter::class)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao():NewsDao
    companion object{
        @Volatile
        private var INSTANCE: NewsDatabase?= null

        @Synchronized
        fun getInstance(context: Context):NewsDatabase{
            if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as NewsDatabase
        }
    }
}