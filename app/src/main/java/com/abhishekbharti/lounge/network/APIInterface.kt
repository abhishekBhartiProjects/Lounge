package com.abhishekbharti.lounge.network

import com.abhishekbharti.lounge.requestBody.CompletionRequestBody
import com.abhishekbharti.lounge.requestBody.GenerateImageRequestBody
import com.abhishekbharti.lounge.response.CompletionResponse
import com.abhishekbharti.lounge.response.GenerateImageResponse
import com.abhishekbharti.lounge.response.TranscriptionResponse
import com.abhishekbharti.lounge.response.UserDetailsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIInterface {

    @POST("v1/completions")
    suspend fun postPrompt(
        @Body completionRequestBody: CompletionRequestBody
    ): Response<CompletionResponse>

    @POST("v1/images/generations")
    suspend fun generateImage(
        @Body generateImageRequestBody: GenerateImageRequestBody
    ): Response<GenerateImageResponse>

    @Multipart
    @POST("v1/audio/transcriptions")
    suspend fun transcribeAudio(
        @Part file: MultipartBody.Part,
        @Part("file") name: RequestBody,
        @Part("model") model: String
    ): Response<TranscriptionResponse>

    @GET("users/me")
    suspend fun getUserDetails():Response<UserDetailsResponse>
}