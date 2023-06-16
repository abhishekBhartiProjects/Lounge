package com.abhishekbharti.lounge.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.base.ImageManager
import com.abhishekbharti.lounge.databinding.EditProfileFragmentBinding
import com.abhishekbharti.lounge.databinding.ProfileFragmentBinding
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.response.ProfileResponse


class ProfileFragment: Fragment() {
    var mBinding: ProfileFragmentBinding? = null
    val viewModel: ProfileViewModel by viewModels()
    private var mId: Int = -1

    companion object {
        fun newInstance(bundle: Bundle): ProfileFragment = ProfileFragment().apply {
            arguments = bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ProfileFragmentBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundle()
        initViewModel()
        viewModel.getProfileDetails(mId)
    }

    private fun initViewModel(){
        viewModel.profileResponseMLD.observe(viewLifecycleOwner){
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    onGetProfileSuccess(it.data as ProfileResponse)
                }
                else -> {}
            }
        }
    }

    private fun initBundle(){
        mId = arguments?.getInt("id") ?: -1
    }

    private fun onGetProfileSuccess(profileResponse: ProfileResponse){
        mBinding?.apply {
            profileResponse.user?.let {user->
                ImageManager.loadImage(coverPicIv, user.image)
                nameTv.text = user.name
                emailTv.text = user.email
                phoneTv.text = user.phone
            }
        }

    }
}