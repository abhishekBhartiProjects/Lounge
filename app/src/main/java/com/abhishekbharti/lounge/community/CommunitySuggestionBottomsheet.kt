package com.abhishekbharti.lounge.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.CommunitySuggestionBottomsheetBinding
import com.abhishekbharti.lounge.profile.EditProfileFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            val frag = CommunitySuggestionFragment.newInstance()

            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.replace(R.id.fragmentContainer, frag, "CommunitySuggestionFragment")
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

//    override fun onStart() {
//        super.onStart()
//        view?.viewTreeObserver?.addOnGlobalLayoutListener {
//            val dialog = dialog as BottomSheetDialog
//            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
//            bottomSheet?.let {
//                val behavior = BottomSheetBehavior.from(it)
//                behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                behavior.peekHeight = bottomSheet.height
//                behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                    override fun onStateChanged(bottomSheet: View, newState: Int) {
//                        if (newState == BottomSheetBehavior.STATE_DRAGGING) {
//                            behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                        }
//                    }
//
//                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                        //nothing
//                    }
//                })
//                bottomSheet.setBackgroundResource(android.R.color.transparent)
//            }
//        }
//    }
}