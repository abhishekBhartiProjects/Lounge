package com.abhishekbharti.lounge.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhishekbharti.lounge.response.FeedPost

class HomeAdapter(val viewModel: HomeViewModel): ListAdapter<Any, RecyclerView.ViewHolder>(HomeDiffcallback())  {

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        when(getItem(position)) {
            is FeedPost -> { return HomePostViewHolder.LAYOUT }
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder : RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when(viewType) {
            HomePostViewHolder.LAYOUT -> {
                viewHolder = HomePostViewHolder.create(inflater, parent, viewModel)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder) {
            is HomePostViewHolder -> holder.bind(item as FeedPost, position)
        }
    }
}