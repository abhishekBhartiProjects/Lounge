package com.abhishekbharti.lounge.response

data class FeedPostResponse(
    val has_next: Boolean,
    val post: List<FeedPost>,
    val success: Boolean
)