package com.abhishekbharti.lounge.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.ItemPostBinding
import com.abhishekbharti.lounge.response.FeedPost

class HomePostViewHolder(
    val binding: ItemPostBinding,
    val viewModel: HomeViewModel
): RecyclerView.ViewHolder(binding.root) {

    companion object {
        val LAYOUT = R.layout.item_post

        fun create(
            inflater: LayoutInflater,
            viewGroup: ViewGroup,
            viewModel: HomeViewModel
        ): HomePostViewHolder {
            val binding = DataBindingUtil.inflate<ItemPostBinding>(
                inflater,
                LAYOUT,
                viewGroup,
                false
            )

            return HomePostViewHolder(binding, viewModel)
        }
    }

    fun bind(post: FeedPost, position: Int) {

    }
}