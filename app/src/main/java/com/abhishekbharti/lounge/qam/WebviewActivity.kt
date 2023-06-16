package com.abhishekbharti.lounge.qam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.HomeActivityBinding
import com.abhishekbharti.lounge.databinding.WebviewActivityBinding

class WebviewActivity : AppCompatActivity() {
    private lateinit var mBinding: WebviewActivityBinding
    private var mUrl = "https://kukufm.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = WebviewActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initExtras()
        initView()
    }

    private fun initExtras(){
        mUrl = intent.getStringExtra("url") ?: ""
    }

    private fun initView(){
        mBinding.apply {
            webview.loadUrl(mUrl)
        }
    }
}