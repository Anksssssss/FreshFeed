package com.example.freshfeed.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.freshfeed.MyApplication
import com.example.freshfeed.api.Resource
import com.example.freshfeed.models.Article
import com.example.freshfeed.models.SavedArticles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedNewsViewModel(application: Application): AndroidViewModel(application) {

    val repository = (application as MyApplication).newsRepository
    val savedNewsLiveData = MutableLiveData<Resource<List<SavedArticles>>>()

    fun getSavedNewsArticles(){
        savedNewsLiveData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = repository.getAllSavedArticles()
                if(response==null){
                    savedNewsLiveData.postValue(Resource.Error("Nothing Saved"))
                }else
                savedNewsLiveData.postValue(Resource.Success(data = response))
            }catch(e:Exception){
                savedNewsLiveData.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun deleteSavedArticle(article: SavedArticles) {
        viewModelScope.launch {
            repository.deleteSavedArticle(article)
        }
    }

}