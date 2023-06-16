package com.abhishekbharti.lounge.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhishekbharti.lounge.base.BaseViewModel
import com.abhishekbharti.lounge.home.HomeRepo
import com.abhishekbharti.lounge.network.RequestResult
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class EditProfileViewModel: BaseViewModel()  {
    private val repo = ProfileRepo()

    var updateProfileResponseMLD: MutableLiveData<RequestResult<Any?>> = MutableLiveData()

    fun updateProfile(name: RequestBody, avatar: MultipartBody.Part?){
        viewModelScope.launch(exceptionHandler){
            try{
                updateProfileResponseMLD.value = RequestResult.Loading("")
                val result = repo.updateProfile(name, avatar)
                updateProfileResponseMLD.value = result
            } catch (e: Exception){
                updateProfileResponseMLD.value = RequestResult.OtherError(e)
            }
        }
    }
}