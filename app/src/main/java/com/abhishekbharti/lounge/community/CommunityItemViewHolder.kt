package com.abhishekbharti.lounge.community

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.base.ImageManager
import com.abhishekbharti.lounge.common.SharedPreferenceManager
import com.abhishekbharti.lounge.databinding.ItemCommunityBinding
import com.abhishekbharti.lounge.databinding.ItemPostBinding
import com.abhishekbharti.lounge.home.HomeActivity
import com.abhishekbharti.lounge.home.HomePostViewHolder
import com.abhishekbharti.lounge.home.HomeViewModel
import com.abhishekbharti.lounge.response.Community
import com.abhishekbharti.lounge.response.FeedPost

class CommunityItemViewHolder(
    val binding: ItemCommunityBinding,
    val viewModel: CommunityViewModel
): RecyclerView.ViewHolder(binding.root) {

    companion object {
        val LAYOUT = R.layout.item_community

        fun create(
            inflater: LayoutInflater,
            viewGroup: ViewGroup,
            viewModel: CommunityViewModel
        ): CommunityItemViewHolder {
            val binding = DataBindingUtil.inflate<ItemCommunityBinding>(
                inflater,
                LAYOUT,
                viewGroup,
                false
            )

            return CommunityItemViewHolder(binding, viewModel)
        }
    }

    fun bind(community: Community, position: Int) {
        binding.apply {
            titleTv.text = community.name
            ImageManager.loadImage(dpIv, community.logo)

            binding.root.setOnClickListener {
                SharedPreferenceManager.setCurrentCommunityId(community.id)

                it.context.startActivity(Intent(it.context, HomeActivity::class.java))

            }
        }
    }
}