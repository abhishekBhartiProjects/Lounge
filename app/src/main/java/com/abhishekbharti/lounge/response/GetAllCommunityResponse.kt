package com.abhishekbharti.lounge.response

data class GetAllCommunityResponse(
    val communities: List<Community>,
    val has_next: Boolean,
    val success: Boolean
)