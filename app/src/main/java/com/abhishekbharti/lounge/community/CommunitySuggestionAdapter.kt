package com.abhishekbharti.lounge.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abhishekbharti.lounge.home.HomeDiffcallback
import com.abhishekbharti.lounge.response.Community

class CommunitySuggestionAdapter(val viewModel: CommunityViewModel): ListAdapter<Any, RecyclerView.ViewHolder>(
    HomeDiffcallback()
)  {

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        when(getItem(position)) {
            is Community -> { return CommunityItemViewHolder.LAYOUT }
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder : RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when(viewType) {
            CommunityItemViewHolder.LAYOUT -> {
                viewHolder = CommunityItemViewHolder.create(inflater, parent, viewModel)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder) {
            is CommunityItemViewHolder -> holder.bind(item as Community, position)
        }
    }
}