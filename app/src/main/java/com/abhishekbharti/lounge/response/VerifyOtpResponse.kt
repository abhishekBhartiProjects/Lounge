package com.abhishekbharti.lounge.response

data class VerifyOtpResponse(
    val message: String,
    val success: Boolean,
    val token: String
)