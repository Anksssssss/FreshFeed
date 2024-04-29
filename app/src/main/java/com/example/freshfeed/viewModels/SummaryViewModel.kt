package com.example.freshfeed.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freshfeed.api.Resource
import com.example.freshfeed.models.Summary
import com.example.freshfeed.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SummaryViewModel: ViewModel(){

    val summaryLiveData = MutableLiveData<Resource<Summary>>()
    lateinit var repository: Repository

    fun getSummary(url: String, sentences: Int){
        summaryLiveData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = repository.getSummary(url,sentences)
                if(response == null){
                    summaryLiveData.postValue(Resource.Error("No data"))
                }else
                    summaryLiveData.postValue(response)
            }catch (e: Exception){
                summaryLiveData.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

}