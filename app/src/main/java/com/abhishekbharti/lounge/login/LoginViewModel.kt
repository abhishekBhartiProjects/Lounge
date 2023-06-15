package com.abhishekbharti.lounge.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhishekbharti.lounge.base.BaseViewModel
import com.abhishekbharti.lounge.network.RequestResult
import kotlinx.coroutines.launch

class LoginViewModel: BaseViewModel() {
    private val repo = LoginRepo()
    var sendOtpResponseMLD: MutableLiveData<RequestResult<Any?>> = MutableLiveData()
    var verifyOtpResponseMLD: MutableLiveData<RequestResult<Any?>> = MutableLiveData()
    var getProfileResponseMLD: MutableLiveData<RequestResult<Any?>> = MutableLiveData()

    fun sendOtp(phone: String, countryCode: String){
        viewModelScope.launch(exceptionHandler) {
            try {
                sendOtpResponseMLD.value = RequestResult.Loading("")
                val result = repo.sendOtp(phone, countryCode)
                sendOtpResponseMLD.value = result
            } catch (e: Exception) {
                sendOtpResponseMLD.value = RequestResult.OtherError(e)
            }
        }
    }

    fun verifyOtp(phone: String, code: String){
        viewModelScope.launch(exceptionHandler) {
            try {
                verifyOtpResponseMLD.value = RequestResult.Loading("")
                val result = repo.verifyOtp(phone, code)
                verifyOtpResponseMLD.value = result
            } catch (e: Exception) {
                verifyOtpResponseMLD.value = RequestResult.OtherError(e)
            }
        }
    }

    fun getProfile(){
        viewModelScope.launch(exceptionHandler) {
            try {
                getProfileResponseMLD.value = RequestResult.Loading("")
                val result = repo.getProfile()
                getProfileResponseMLD.value = result
            } catch (e: Exception) {
                getProfileResponseMLD.value = RequestResult.OtherError(e)
            }
        }
    }
}