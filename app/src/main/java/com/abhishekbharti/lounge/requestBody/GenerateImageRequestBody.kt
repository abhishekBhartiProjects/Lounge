package com.abhishekbharti.lounge.requestBody

data class GenerateImageRequestBody(
    val n: Int,
    val prompt: String,
    val size: String
)
