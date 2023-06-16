package com.abhishekbharti.lounge.community

import android.content.Intent
import android.os.Bundle
import android.os.SharedMemory
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.common.SharedPreferenceManager
import com.abhishekbharti.lounge.databinding.CommunitySuggestionFragmentBinding
import com.abhishekbharti.lounge.databinding.CreateCommunityFragmentBinding
import com.abhishekbharti.lounge.home.HomeActivity
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.response.CreateCommunityResponse
import com.abhishekbharti.lounge.response.GetAllCommunityResponse

class CreateCommunityFragment : Fragment() {
    var mBinding: CreateCommunityFragmentBinding? = null
    private var mViewModel: CommunityViewModel? = null

    companion object {
        fun newInstance(viewModel: CommunityViewModel): CreateCommunityFragment = CreateCommunityFragment().apply {
            mViewModel = viewModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = CreateCommunityFragmentBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initViewModel(){
        mViewModel?.createCommunityResponseMLD?.observe(viewLifecycleOwner){
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    onCreateCommunitySuccess(it.data as CreateCommunityResponse)
                }
                else ->{}
            }
        }
    }

    private fun initView(){
        mBinding?.apply {
            createBtn.setOnClickListener {
                if(communityNameEt.text.isNullOrEmpty()){
                    Toast.makeText(context, "Please enter community name", Toast.LENGTH_SHORT).show()
                } else {
                    mViewModel?.createCommunity(communityNameEt.text.toString())
                }

            }
        }
    }

    private fun onCreateCommunitySuccess(createCommunityResponse: CreateCommunityResponse){
        SharedPreferenceManager.setCurrentCommunityId(createCommunityResponse.id)
        startActivity(Intent(requireContext(), HomeActivity::class.java))
        activity?.finish()
    }
}