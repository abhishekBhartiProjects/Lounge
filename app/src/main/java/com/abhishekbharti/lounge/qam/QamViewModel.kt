package com.abhishekbharti.lounge.qam

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhishekbharti.lounge.base.BaseViewModel
import com.abhishekbharti.lounge.network.RequestResult
import kotlinx.coroutines.launch
import java.lang.Exception

class QamViewModel: BaseViewModel() {
    private val repo = QamRepo()

    var createQamResponseMLD: MutableLiveData<RequestResult<Any?>> = MutableLiveData()

    fun createQam(communityId: Int, qamName: String, qamLink: String){
        viewModelScope.launch(exceptionHandler) {
            try {
                createQamResponseMLD.value = RequestResult.Loading("")
                val result = repo.createQam(communityId, qamName, qamLink)
                createQamResponseMLD.value = result
            } catch (e: Exception) {
                createQamResponseMLD.value = RequestResult.OtherError(e)
            }
        }
    }
}