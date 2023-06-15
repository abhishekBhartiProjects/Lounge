package com.abhishekbharti.lounge.response

data class ProfileResponse(
    val success: Boolean,
    val user: User? = null
)