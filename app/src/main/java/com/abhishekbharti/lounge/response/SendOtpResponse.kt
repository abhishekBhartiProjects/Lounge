package com.abhishekbharti.lounge.response

data class SendOtpResponse(
    val message: String,
    val otp: Int,
    val success: Boolean
)