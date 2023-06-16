package com.abhishekbharti.lounge.community

import com.abhishekbharti.lounge.base.BaseRepo
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.requestBody.CompletionRequestBody
import com.abhishekbharti.lounge.requestBody.CreateCommunityRequestBody
import com.abhishekbharti.lounge.response.CompletionResponse
import com.abhishekbharti.lounge.response.CreateCommunityResponse
import com.abhishekbharti.lounge.response.FeedPostResponse
import com.abhishekbharti.lounge.response.GetAllCommunityResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommunityRepo: BaseRepo() {

    suspend fun getAllCommunity(): RequestResult<GetAllCommunityResponse?> {
        return withContext(Dispatchers.IO) {
            return@withContext handleCommonResponse(
                {
                    apiInterface.getAllCommunity()
                },
                { RequestResult.Success(it.body()) }
            )

        }
    }

    suspend fun createCommunity(name:String): RequestResult<CreateCommunityResponse?> {
        return withContext(Dispatchers.IO){
            return@withContext handleCommonResponse(
                {
                    val body = CreateCommunityRequestBody(
                        name = name
                    )
                    apiInterface.createCommunity(body)
                },
                {
                    RequestResult.Success(it.body())
                }
            )
        }
    }
}