package com.abhishekbharti.lounge.profile

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhishekbharti.lounge.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class FloatingBottomSheetDialog(var resource: Int,
                                var mData: ArrayList<Any>,
                                var inflater: LayoutInflater,
                                var ct: Context,
                                var listener: (Any, Int) -> Unit) : BottomSheetDialog(ct, R.style.RoundedBottomSheetDialog) {

    init {
        setView()
    }

    private fun setView() {
        val sheetView = inflater.inflate(resource, null)
        val adapter = CommonListAdapter(ct, mData)
        val recyclerView = sheetView?.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.addItemDecoration(DividerItemDecorator(ContextCompat.getDrawable(context, R.drawable.separator)))
        recyclerView?.adapter = adapter
        adapter.setClickListener(object : CommonListAdapter.ItemClickListener {
            override fun onItemClick(item: Any, itemPosition: Int) {
                listener(item, itemPosition)
            }
        })

        setContentView(sheetView)
    }

    inner class DividerItemDecorator(private val mDivider: Drawable?) : RecyclerView.ItemDecoration() {

        override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val dividerLeft = parent.paddingLeft
            val dividerRight = parent.width - parent.paddingRight

            val childCount = parent.childCount
            for (i in 0..childCount - 2) {
                val child = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val dividerTop = child.bottom + params.bottomMargin
                val dividerBottom = dividerTop + mDivider!!.intrinsicHeight

                mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                mDivider.draw(canvas)
            }
        }
    }
}