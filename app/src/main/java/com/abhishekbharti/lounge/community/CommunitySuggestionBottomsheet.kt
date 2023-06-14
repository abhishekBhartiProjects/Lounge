package com.abhishekbharti.lounge.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhishekbharti.lounge.databinding.CommunitySuggestionBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommunitySuggestionBottomsheet: BottomSheetDialogFragment() {
    private var mBinding: CommunitySuggestionBottomsheetBinding? = null

    companion object {
        fun newInstance(bundle: Bundle): CommunitySuggestionBottomsheet =
            CommunitySuggestionBottomsheet().apply {
                arguments = bundle
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleData()
    }

    private fun getBundleData() {
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = CommunitySuggestionBottomsheetBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }
}