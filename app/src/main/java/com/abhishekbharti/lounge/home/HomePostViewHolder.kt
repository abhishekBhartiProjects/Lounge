package com.abhishekbharti.lounge.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.base.ImageManager
import com.abhishekbharti.lounge.databinding.ItemPostBinding
import com.abhishekbharti.lounge.feedPost.FeedPostActivity
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
        binding.apply {
            userNameTv.text = post.user_name
            departmentTv.text = post.user_department
            timeTv.text = post.created_on
            postTextTv.text = post.description
            ImageManager.loadImage(postImageIv, post.image)
            reactionTv.text = "${post.reaction_count} reactions"

            binding.root.setOnClickListener {
                val intent = Intent(it.context, FeedPostActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("post", post)
                intent.putExtras(bundle)

                it.context.startActivity(intent)
            }
        }
    }
}