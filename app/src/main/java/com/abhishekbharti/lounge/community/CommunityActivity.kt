package com.abhishekbharti.lounge.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.CommunityActivityBinding
import com.abhishekbharti.lounge.home.HomeViewModel

class CommunityActivity : AppCompatActivity() {
    private var mBinding: CommunityActivityBinding? = null
    private val viewModel: CommunityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_activity)

    }
}