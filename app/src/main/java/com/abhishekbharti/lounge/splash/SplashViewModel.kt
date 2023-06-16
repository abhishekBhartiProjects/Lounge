package com.abhishekbharti.lounge.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abhishekbharti.lounge.base.BaseViewModel
import com.abhishekbharti.lounge.common.SharedPreferenceManager
import com.abhishekbharti.lounge.network.RequestResult
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashViewModel: BaseViewModel() {
    private val repo = SplashRepo()

}