package com.abhishekbharti.lounge

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.abhishekbharti.lounge.network.APIInterface
import com.abhishekbharti.lounge.network.APIObject

class LoungeApplication: Application(){

    @Volatile
    private var apiInterface: APIInterface? = null
    @Volatile
    private var apiInterfaceWithoutToken: APIInterface? = null

    companion object {
        @Volatile
        private var instance: LoungeApplication? = null
        @Synchronized
        fun getInstance(): LoungeApplication = instance ?: LoungeApplication()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@LoungeApplication
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    @Synchronized
    fun getAPIInterface(): APIInterface = apiInterface ?: APIObject.build()

    @Synchronized
    fun getWithoutTokenIAPIService(): APIInterface = apiInterfaceWithoutToken ?: APIObject.build(30, false)
}