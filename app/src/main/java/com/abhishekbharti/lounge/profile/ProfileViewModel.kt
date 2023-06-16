package com.abhishekbharti.lounge.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhishekbharti.lounge.base.BaseViewModel
import com.abhishekbharti.lounge.network.RequestResult
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel: BaseViewModel() {
    val repo = ProfileRepo()

    var profileResponseMLD: MutableLiveData<RequestResult<Any?>> = MutableLiveData()

    fun getProfileDetails(id: Int){
        viewModelScope.launch(exceptionHandler) {
            try {
                profileResponseMLD.value = RequestResult.Loading("")
                val result = repo.getProfile(id)
                profileResponseMLD.value = result
            } catch (e: Exception) {
                profileResponseMLD.value = RequestResult.OtherError(e)
            }
        }
    }
}