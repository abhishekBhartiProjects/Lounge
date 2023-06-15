package com.abhishekbharti.lounge.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.EditProfileFragmentBinding
import com.abhishekbharti.lounge.databinding.ProfileFragmentBinding


class ProfileFragment: Fragment() {
    var mBinding: ProfileFragmentBinding? = null

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ProfileFragmentBinding.inflate(inflater, container, false)
        return mBinding?.root
    }
}