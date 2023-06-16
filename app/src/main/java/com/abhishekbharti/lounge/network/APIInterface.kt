package com.abhishekbharti.lounge.network

import com.abhishekbharti.lounge.requestBody.CompletionRequestBody
import com.abhishekbharti.lounge.requestBody.GenerateImageRequestBody
import com.abhishekbharti.lounge.requestBody.SendOtpRequestBody
import com.abhishekbharti.lounge.requestBody.VerifyOtpRequestBody
import com.abhishekbharti.lounge.response.CompletionResponse
import com.abhishekbharti.lounge.response.FeedPostResponse
import com.abhishekbharti.lounge.response.GenerateImageResponse
import com.abhishekbharti.lounge.response.ProfileResponse
import com.abhishekbharti.lounge.response.SendOtpResponse
import com.abhishekbharti.lounge.response.TranscriptionResponse
import com.abhishekbharti.lounge.response.UserDetailsResponse
import com.abhishekbharti.lounge.response.VerifyOtpResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @POST("api/v1/users/auth/get-otp/")
    suspend fun sendMobileVerificationOtp(
        @Body requestBody: SendOtpRequestBody
    ): Response<SendOtpResponse>

    @POST("api/v1/users/auth/verify-otp/")
    suspend fun verifyMobileVerificationOtp(
        @Body requestBody: VerifyOtpRequestBody
    ): Response<VerifyOtpResponse>

    @GET("api/v1/users/profile")
    suspend fun getProfile():Response<ProfileResponse>

    @GET("api/v1/posts/get-posts/?community_id=1&page=1")
    suspend fun getFeedPost(
        @Query("community_id") communityId: Int,
        @Query("page") page: Int
    ): Response<FeedPostResponse>

    @Multipart
    @POST("api/v1/users/update-profile/")
    fun updateProfile(
        @Part("name") name: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Response<ProfileResponse>
}