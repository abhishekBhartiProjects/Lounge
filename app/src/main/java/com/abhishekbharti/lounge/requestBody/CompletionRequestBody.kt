package com.abhishekbharti.lounge.requestBody

data class CompletionRequestBody(
    val frequency_penalty: Int,
    val max_tokens: Int,
    val model: String,
    val presence_penalty: Int,
    val prompt: String,
    val temperature: Float,
    val top_p: Int
)