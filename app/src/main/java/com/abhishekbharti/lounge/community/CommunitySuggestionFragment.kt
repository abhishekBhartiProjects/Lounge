package com.abhishekbharti.lounge.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.CommunitySuggestionFragmentBinding
import com.abhishekbharti.lounge.databinding.EditProfileFragmentBinding
import com.abhishekbharti.lounge.home.HomeAdapter
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.profile.EditProfileFragment
import com.abhishekbharti.lounge.response.FeedPostResponse
import com.abhishekbharti.lounge.response.GetAllCommunityResponse

class CommunitySuggestionFragment : Fragment() {
    var mBinding: CommunitySuggestionFragmentBinding? = null
    private var mViewModel: CommunityViewModel? = null
    private var adapter: CommunitySuggestionAdapter? = null
    companion object {
        fun newInstance(viewModel: CommunityViewModel): CommunitySuggestionFragment = CommunitySuggestionFragment().apply {
            mViewModel = viewModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = CommunitySuggestionFragmentBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initViewModel(){
        mViewModel?.getAllCommunityResponseMLD?.observe(viewLifecycleOwner){
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    onGetAllCommunityResponseSuccess(it.data as GetAllCommunityResponse)
                }
                else -> {}
            }
        }
    }

    private fun initView(){
        initAdapter()
        mViewModel?.getAllCommunity()
    }

    private fun onGetAllCommunityResponseSuccess(getAllCommunityResponse: GetAllCommunityResponse){
        adapter?.submitList(getAllCommunityResponse.communities)
    }

    private fun initAdapter(){
        mBinding?.apply {
            adapter = CommunitySuggestionAdapter(mViewModel!!)
            list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            list.adapter = adapter
        }
    }

}