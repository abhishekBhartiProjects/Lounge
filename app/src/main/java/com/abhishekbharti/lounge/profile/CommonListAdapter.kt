package com.abhishekbharti.lounge.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishekbharti.lounge.databinding.ItemBsMediaBinding

class CommonListAdapter(val ct: Context, private val mData: ArrayList<Any>) : RecyclerView.Adapter<CommonListAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(item: Any, itemPosition: Int)
    }

    private var mClickListener: ItemClickListener? = null

    class ViewHolder(val binding: ItemBsMediaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBsMediaBinding.inflate(LayoutInflater.from(ct), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value:String = mData[position] as String
        holder.binding.title.text = value
        holder.binding.title.setOnClickListener {
            mClickListener?.onItemClick(value, position)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

}