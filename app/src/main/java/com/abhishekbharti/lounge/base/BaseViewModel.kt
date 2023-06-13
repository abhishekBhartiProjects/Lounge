package com.abhishekbharti.lounge.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel: ViewModel() {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("Exception: ","$exception")
    }
}