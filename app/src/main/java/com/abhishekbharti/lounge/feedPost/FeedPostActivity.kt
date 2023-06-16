package com.abhishekbharti.lounge.feedPost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.base.ImageManager
import com.abhishekbharti.lounge.databinding.FeedPostActivityBinding
import com.abhishekbharti.lounge.databinding.HomeActivityBinding
import com.abhishekbharti.lounge.home.HomeViewModel
import com.abhishekbharti.lounge.profile.ProfileActivity
import com.abhishekbharti.lounge.response.FeedPost

class FeedPostActivity : AppCompatActivity() {

    private lateinit var mBinding: FeedPostActivityBinding
    private val viewModel: HomeViewModel by viewModels()
    private var mPost: FeedPost? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = FeedPostActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initExtras()
        initView()
    }

    private fun initExtras(){
        val bundle = intent.extras
        mPost = bundle?.getParcelable<FeedPost>("post")
    }

    private fun initView(){
        mPost?.let {post ->
            mBinding.apply {
                userNameTv.text = post.user_name
                departmentTv.text = post.user_department
                timeTv.text = post.created_on
                postTextTv.text = post.description
                ImageManager.loadImage(postImageIv, post.image)
                reactionTv.text = "${post.reaction_count} reactions"

                postImageIv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("id", post.user_id)

                    val intent = Intent(this@FeedPostActivity, ProfileActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }

                //Todo: Get Post Comment
            }
        }
    }
}