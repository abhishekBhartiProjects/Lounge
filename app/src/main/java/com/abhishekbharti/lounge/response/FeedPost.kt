package com.abhishekbharti.lounge.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPost(
    val comment_count: Int,
    val created_on: String,
    val description: String,
    val image: String,
    val is_bookmarked: Boolean,
    val reaction_count: Int,
    val url: String,
    val user_department: String,
    val user_id: Int,
    val user_name: String
): Parcelable