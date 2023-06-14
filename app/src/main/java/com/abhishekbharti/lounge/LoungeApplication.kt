package com.abhishekbharti.lounge

import android.app.Application
import com.abhishekbharti.lounge.network.APIInterface
import com.abhishekbharti.lounge.network.APIObject

class LoungeApplication: Application(){

    @Volatile
    private var apiInterface: APIInterface? = null
    companion object {
        @Volatile
        private var instance: LoungeApplication? = null
        @Synchronized
        fun getInstance(): LoungeApplication = instance ?: LoungeApplication()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@LoungeApplication
    }

    @Synchronized
    fun getAPIInterface(): APIInterface = apiInterface ?: APIObject.build()
}