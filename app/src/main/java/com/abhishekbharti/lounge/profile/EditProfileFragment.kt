package com.abhishekbharti.lounge.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abhishekbharti.lounge.databinding.EditProfileFragmentBinding.inflate
import com.abhishekbharti.lounge.databinding.EditProfileFragmentBinding

class EditProfileFragment: Fragment() {
    var mBinding: EditProfileFragmentBinding? = null

    companion object {
        fun newInstance(): EditProfileFragment = EditProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = inflate(inflater, container, false)
        return mBinding?.root
    }
}