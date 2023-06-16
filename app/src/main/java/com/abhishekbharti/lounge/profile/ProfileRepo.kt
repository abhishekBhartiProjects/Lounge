package com.abhishekbharti.lounge.profile

import com.abhishekbharti.lounge.base.BaseRepo
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.response.FeedPostResponse
import com.abhishekbharti.lounge.response.ProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileRepo: BaseRepo() {

    suspend fun updateProfile(name: RequestBody, avatar: MultipartBody.Part?): RequestResult<ProfileResponse?> {
        return withContext(Dispatchers.IO){
            return@withContext handleCommonResponse(
                {
                    apiInterface.updateProfile(name, avatar)
                },
                {
                    RequestResult.Success(it.body())
                }
            )
        }
    }

    suspend fun getProfile(id: Int): RequestResult<ProfileResponse?> {
        return withContext(Dispatchers.IO) {
            return@withContext handleCommonResponse(
                {
                    apiInterface.getMember(id)
                },
                { RequestResult.Success(it.body()) }
            )

        }
    }
}