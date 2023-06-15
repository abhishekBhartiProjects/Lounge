package com.abhishekbharti.lounge.common

import android.content.Context
import com.abhishekbharti.lounge.LoungeApplication

object SharedPreferenceManager {
    private val mSharedPreferences = LoungeSharedPreference

    private val USER_SESSION_TOKEN = "user_session_token"
    private val CURRENT_COMMUNITY_ID = "current_community_id"

    fun clear() {
        mSharedPreferences.clearPrefs()
    }

    fun setUserSessionToken(token: String) = mSharedPreferences.setString(USER_SESSION_TOKEN, token)
    fun getUserSessionToken(): String = mSharedPreferences.getString(USER_SESSION_TOKEN,"") ?: ""

    fun setCurrentCommunityId(id: Int) = mSharedPreferences.setInt(CURRENT_COMMUNITY_ID, id)
    fun getCurrentCommunityId(): Int = mSharedPreferences.getInt(CURRENT_COMMUNITY_ID, -1)

}