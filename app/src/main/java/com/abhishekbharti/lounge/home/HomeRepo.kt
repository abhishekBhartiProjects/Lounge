package com.abhishekbharti.lounge.home

import com.abhishekbharti.lounge.base.BaseRepo
import com.abhishekbharti.lounge.common.PhoneNumberUtils
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.requestBody.CompletionRequestBody
import com.abhishekbharti.lounge.requestBody.GenerateImageRequestBody
import com.abhishekbharti.lounge.requestBody.SendOtpRequestBody
import com.abhishekbharti.lounge.response.CompletionResponse
import com.abhishekbharti.lounge.response.FeedPostResponse
import com.abhishekbharti.lounge.response.GenerateImageResponse
import com.abhishekbharti.lounge.response.GetCommunityDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepo: BaseRepo() {

    suspend fun getFeedPost(communityId: Int, page: Int): RequestResult<FeedPostResponse?> {
        return withContext(Dispatchers.IO) {
            return@withContext handleCommonResponse(
                {
                    apiInterface.getFeedPost(communityId, page)
                },
                { RequestResult.Success(it.body()) }
            )

        }
    }

    suspend fun getCommunityDetails(communityId: Int): RequestResult<GetCommunityDetailResponse?> {
        return withContext(Dispatchers.IO) {
            return@withContext handleCommonResponse(
                {
                    apiInterface.getCommunityDetails(communityId)
                },
                { RequestResult.Success(it.body()) }
            )

        }
    }

    suspend fun postPrompt(prompt:String): RequestResult<CompletionResponse?> {
        return withContext(Dispatchers.IO){
            return@withContext handleCommonResponse(
                {
                    val body = CompletionRequestBody(
                        frequency_penalty = 0,
                        max_tokens = 4000,
                        model = "text-davinci-003",
                        presence_penalty = 0,
                        prompt = prompt,
                        temperature = 0.7F,
                        top_p = 1
                    )
                    apiInterface.postPrompt(body)
                },
                {
                    RequestResult.Success(it.body())
                }
            )
        }
    }

    suspend fun generateImage(prompt: String): RequestResult<GenerateImageResponse?>{
        return withContext(Dispatchers.IO){
            return@withContext handleCommonResponse(
                {
                    val body = GenerateImageRequestBody(1, prompt, "256x256")
                    apiInterface.generateImage(body)
                },
                {
                    RequestResult.Success(it.body())
                }
            )
        }
    }
}