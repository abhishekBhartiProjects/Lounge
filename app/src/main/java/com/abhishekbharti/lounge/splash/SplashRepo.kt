package com.abhishekbharti.lounge.splash

import com.abhishekbharti.lounge.base.BaseRepo
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.requestBody.CompletionRequestBody
import com.abhishekbharti.lounge.response.CompletionResponse
import com.abhishekbharti.lounge.response.ProfileResponse
import com.abhishekbharti.lounge.response.UserDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SplashRepo: BaseRepo() {

//    suspend fun getProfile(): RequestResult<ProfileResponse?> {
//        return withContext(Dispatchers.IO){
//            return@withContext handleCommonResponse(
//                {
//                    apiInterface.getProfile()
//                },
//                {
//                    RequestResult.Success(it.body())
//                }
//            )
//        }
//    }
}