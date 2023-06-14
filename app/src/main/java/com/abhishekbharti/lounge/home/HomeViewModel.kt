package com.abhishekbharti.lounge.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhishekbharti.lounge.base.BaseViewModel
import com.abhishekbharti.lounge.network.RequestResult
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel: BaseViewModel() {
    private val repo = HomeRepo()

    var promptResponseMLD: MutableLiveData<RequestResult<Any?>> = MutableLiveData()
    var generateImageResponseMLD: MutableLiveData<RequestResult<Any?>> = MutableLiveData()

    fun postPrompt(prompt: String){
        viewModelScope.launch(exceptionHandler){
            try{
                promptResponseMLD.value = RequestResult.Loading("")
                val result = repo.postPrompt(prompt)
                promptResponseMLD.value = result
            } catch (e: Exception){
                promptResponseMLD.value = RequestResult.OtherError(e)
            }
        }
    }

    fun generateImage(prompt: String){
        viewModelScope.launch(exceptionHandler){
            try{
                generateImageResponseMLD.value = RequestResult.Loading("")
                val result = repo.generateImage(prompt)
                generateImageResponseMLD.value = result
            } catch (e: Exception){
                generateImageResponseMLD.value = RequestResult.OtherError(e)
            }
        }
    }
}