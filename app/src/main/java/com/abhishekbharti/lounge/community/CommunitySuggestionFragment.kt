package com.abhishekbharti.lounge.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
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
    private val viewModel: CommunityViewModel by viewModels()
    private var adapter: CommunitySuggestionAdapter? = null
    companion object {
        fun newInstance(): CommunitySuggestionFragment = CommunitySuggestionFragment().apply {

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
        viewModel.getAllCommunityResponseMLD.observe(viewLifecycleOwner){
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
        viewModel.getAllCommunity()
        initClickListener()
    }

    private fun onGetAllCommunityResponseSuccess(getAllCommunityResponse: GetAllCommunityResponse){
        adapter?.submitList(getAllCommunityResponse.communities)
    }

    private fun initAdapter(){
        mBinding?.apply {
            adapter = CommunitySuggestionAdapter(viewModel)
            list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            list.adapter = adapter
        }
    }

    private fun initClickListener(){
        mBinding?.apply {
            createTv.setOnClickListener {
                val frag = CreateCommunityFragment.newInstance(viewModel)

                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction?.replace(R.id.fragmentContainer, frag, "CommunitySuggestionFragment")
                fragmentTransaction?.commitAllowingStateLoss()
            }
        }
    }

}