package com.abhishekbharti.lounge.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.CommunityActivityBinding
import com.abhishekbharti.lounge.home.HomeViewModel
import com.abhishekbharti.lounge.profile.EditProfileFragment

class CommunityActivity : AppCompatActivity() {
    private var mBinding: CommunityActivityBinding? = null
    private val viewModel: CommunityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_activity)

        initView()

    }

    private fun initView(){
        val bundle = Bundle()
        val frag = CommunitySuggestionFragment.newInstance(viewModel)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.replace(R.id.fragmentContainer, frag, "CommunitySuggestionFragment")
        fragmentTransaction.commitAllowingStateLoss()
    }


}