package com.abhishekbharti.lounge.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    val message: String,
    val success: Boolean,
    val token: String
)