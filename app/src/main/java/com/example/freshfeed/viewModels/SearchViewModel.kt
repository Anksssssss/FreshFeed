package com.example.freshfeed.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freshfeed.MyApplication
import com.example.freshfeed.api.Resource
import com.example.freshfeed.models.TopHeadlines
import com.example.freshfeed.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel:ViewModel(){

    val searchNewsLiveData = MutableLiveData<Resource<TopHeadlines>>()
    lateinit var repository:Repository

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