package com.abhishekbharti.lounge.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.abhishekbharti.lounge.LoungeApplication
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.common.SharedPreferenceManager
import com.abhishekbharti.lounge.databinding.SplashActivityBinding
import com.abhishekbharti.lounge.home.HomeViewModel
import com.abhishekbharti.lounge.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var mBinding: SplashActivityBinding
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initViews()
    }

    private fun initViews(){
        if(true || SharedPreferenceManager.getUserSessionToken().isNotEmpty()){
            getUserDetails()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                openLogin()
            }, 2000)
        }
    }

    private fun openLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun getUserDetails(){
        viewModel.getUserDetails()
    }


}