package com.abhishekbharti.lounge.response

data class User(
    val created_at: String,
    val email: String,
    val image: String,
    val name: String,
    val phone: String
)