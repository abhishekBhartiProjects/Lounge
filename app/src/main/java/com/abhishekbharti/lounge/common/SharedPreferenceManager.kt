package com.abhishekbharti.lounge.common

import android.content.Context
import com.abhishekbharti.lounge.LoungeApplication

object SharedPreferenceManager {
    private val mSharedPreferences = LoungeSharedPreference

    private val USER_SESSION_TOKEN = "user_session_token"

    fun clear() {
        mSharedPreferences.clearPrefs()
    }

    fun setUserSessionToken(token: String) = mSharedPreferences.setString(USER_SESSION_TOKEN, token)
    fun getUserSessionToken(): String = mSharedPreferences.getString(USER_SESSION_TOKEN,"") ?: ""

}