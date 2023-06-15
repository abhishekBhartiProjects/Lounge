package com.abhishekbharti.lounge.home

import androidx.recyclerview.widget.DiffUtil

class HomeDiffcallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return true
    }
}