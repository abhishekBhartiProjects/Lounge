package com.abhishekbharti.lounge.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.HomeActivityBinding
import com.abhishekbharti.lounge.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}