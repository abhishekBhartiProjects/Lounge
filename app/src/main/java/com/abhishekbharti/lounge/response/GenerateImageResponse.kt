package com.abhishekbharti.lounge.response

data class GenerateImageResponse(
    val created: Int,
    val `data`: List<Data>
)