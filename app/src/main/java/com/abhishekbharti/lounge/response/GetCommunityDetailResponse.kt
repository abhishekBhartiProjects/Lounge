package com.abhishekbharti.lounge.response

data class GetCommunityDetailResponse(
    val community: CommunityX,
    val community_qams: List<CommunityQam>,
    val success: Boolean
)