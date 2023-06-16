package com.abhishekbharti.lounge.qam

import com.abhishekbharti.lounge.base.BaseRepo
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.requestBody.CompletionRequestBody
import com.abhishekbharti.lounge.requestBody.QamRequestBody
import com.abhishekbharti.lounge.response.CompletionResponse
import com.abhishekbharti.lounge.response.CreateQamResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QamRepo: BaseRepo() {

    suspend fun createQam(communityId: Int, qamName: String, qamLink: String): RequestResult<CreateQamResponse?> {
        return withContext(Dispatchers.IO){
            return@withContext handleCommonResponse(
                {
                    val body = QamRequestBody(
                        community_id = communityId,
                        title = qamName,
                        link = qamLink
                    )
                    apiInterface.createQam(body)
                },
                {
                    RequestResult.Success(it.body())
                }
            )
        }
    }
}