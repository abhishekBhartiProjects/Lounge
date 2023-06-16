package com.abhishekbharti.lounge.qam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.common.SharedPreferenceManager
import com.abhishekbharti.lounge.databinding.HomeActivityBinding
import com.abhishekbharti.lounge.databinding.QamActivityBinding
import com.abhishekbharti.lounge.home.HomeActivity
import com.abhishekbharti.lounge.home.HomeViewModel
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.response.FeedPostResponse

class QamActivity : AppCompatActivity() {
    private lateinit var mBinding: QamActivityBinding
    private val viewModel: QamViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = QamActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initViewModel()
        initView()


    }

    private fun initViewModel(){
        viewModel.createQamResponseMLD.observe(this){
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    Toast.makeText(this, "QAM created successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                else -> {}
            }
        }
    }

    private fun initView(){
        mBinding.apply {
            saveBtn.setOnClickListener {
                if(nameEt.text.isNotEmpty() && linkEt.text.isNotEmpty()){
                    viewModel.createQam(SharedPreferenceManager.getCurrentCommunityId(), nameEt.text.toString(), linkEt.text.toString())
                } else {
                    Toast.makeText(this@QamActivity, "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }

            cancelBtn.setOnClickListener {
                onBackPressed()
            }
        }
    }
}