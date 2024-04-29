package com.example.freshfeed.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freshfeed.MyApplication
import com.example.freshfeed.api.Resource
import com.example.freshfeed.models.SavedArticles
import com.example.freshfeed.models.TopHeadlines
import com.example.freshfeed.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(application: Application):AndroidViewModel(application) {

    val repository = (application as MyApplication).newsRepository
    val generalNewsLiveData = MutableLiveData<Resource<TopHeadlines>>()
    val businessNewsLiveData = MutableLiveData<Resource<TopHeadlines>>()
    val entertainmentNewsLiveData = MutableLiveData<Resource<TopHeadlines>>()
    val healthNewsLiveData = MutableLiveData<Resource<TopHeadlines>>()
    val scienceNewsLiveData = MutableLiveData<Resource<TopHeadlines>>()
    val sportsNewsLiveData = MutableLiveData<Resource<TopHeadlines>>()
    val technologyNewsLiveData = MutableLiveData<Resource<TopHeadlines>>()
    val searchNewsLiveData = MutableLiveData<Resource<TopHeadlines>>()

    fun getTopHeadlines(category: String = "general") {
        // Based on the category, update the appropriate LiveData
        val liveDataToUpdate = when (category) {
            "general" -> generalNewsLiveData
            "business" -> businessNewsLiveData
            "entertainment" -> entertainmentNewsLiveData
            "health" -> healthNewsLiveData
            "science" -> scienceNewsLiveData
            "sports" -> sportsNewsLiveData
            "technology" -> technologyNewsLiveData
            else -> throw IllegalArgumentException("Invalid category: $category")
        }

        liveDataToUpdate.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getTopHeadlines("in", category)
                for(article in response.data!!.articles){
                    article.category = category
                }
                liveDataToUpdate.postValue(response)
            } catch (e: Exception) {
                liveDataToUpdate.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun searchNews(query:String){
        searchNewsLiveData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = repository.getSearchedNews(query)
                searchNewsLiveData.postValue(response)
            }catch (e: Exception){
                searchNewsLiveData.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

}