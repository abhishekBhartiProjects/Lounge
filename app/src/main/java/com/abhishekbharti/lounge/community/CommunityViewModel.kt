package com.abhishekbharti.lounge.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhishekbharti.lounge.base.BaseViewModel
import com.abhishekbharti.lounge.network.RequestResult
import kotlinx.coroutines.launch
import java.lang.Exception

class CommunityViewModel: BaseViewModel() {
    private val repo = CommunityRepo()

    var getAllCommunityResponseMLD: MutableLiveData<RequestResult<Any?>> = MutableLiveData()

    fun getAllCommunity(){
        viewModelScope.launch(exceptionHandler) {
            try {
                getAllCommunityResponseMLD.value = RequestResult.Loading("")
                val result = repo.getAllCommunity()
                getAllCommunityResponseMLD.value = result
            } catch (e: Exception) {
                getAllCommunityResponseMLD.value = RequestResult.OtherError(e)
            }
        }
    }

}