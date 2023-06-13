package com.abhishekbharti.lounge

import android.app.Application
import com.abhishekbharti.lounge.network.APIInterface
import com.abhishekbharti.lounge.network.APIObject

class LoungeApplication: Application() {

    companion object {
        @Volatile
        private var instance: LoungeApplication? = null
        @Synchronized
        fun getInstance(): LoungeApplication = instance ?: LoungeApplication()
    }

    @Volatile
    private var apiInterface: APIInterface? = null

    override fun onCreate() {
        super.onCreate()
    }

    @Synchronized
    fun getAPIInterface(): APIInterface = apiInterface ?: APIObject.build()
}