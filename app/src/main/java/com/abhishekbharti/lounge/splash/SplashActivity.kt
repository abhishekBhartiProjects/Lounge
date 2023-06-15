package com.abhishekbharti.lounge.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import com.abhishekbharti.lounge.LoungeApplication
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.common.SharedPreferenceManager
import com.abhishekbharti.lounge.community.CommunityActivity
import com.abhishekbharti.lounge.databinding.SplashActivityBinding
import com.abhishekbharti.lounge.home.HomeActivity
import com.abhishekbharti.lounge.home.HomeViewModel
import com.abhishekbharti.lounge.login.LoginActivity
import com.abhishekbharti.lounge.login.LoginViewModel
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.profile.ProfileActivity
import com.abhishekbharti.lounge.response.ProfileResponse
import com.abhishekbharti.lounge.response.UserDetailsResponse

class SplashActivity : AppCompatActivity() {
    private lateinit var mBinding: SplashActivityBinding
    private val viewModel: SplashViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initViewModelObserver()
        initViews()
    }

    private fun initViewModelObserver(){
        viewModel.getUserDetailsResponseMLD.observe(this){
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> { onGetUserSuccess(it.data as UserDetailsResponse)}
                else -> { Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()}
            }
        }

        loginViewModel.getProfileResponseMLD.observe(this) {
            when(it) {
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    onGetProfileResponseSuccess(it.data as ProfileResponse)
                }
                else -> {
                    navigateToNextScreen(null)
                }
            }
        }
    }

    private fun initViews(){
        mBinding.apply {
            val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_in)
            startAnimation(logoCl, animation)
        }
    }

    private fun startAnimation(view: View, animation: Animation) {
        animation.duration = 1500
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = View.VISIBLE
                initNavigation()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
        view.startAnimation(animation)
    }

    private fun initNavigation(){

        if(SharedPreferenceManager.getUserSessionToken().isNotEmpty()){
            getProfile()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                openLogin()
            }, 2000)
        }
    }

    private fun onGetUserSuccess(userDetailsResponse: UserDetailsResponse){

    }

    private fun openLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
    private fun getProfile(){
        loginViewModel.getProfile()
    }

    private fun onGetProfileResponseSuccess(profileResponse: ProfileResponse){
        navigateToNextScreen(profileResponse)
    }

    private fun navigateToNextScreen(profileResponse: ProfileResponse?){
        if(profileResponse == null || profileResponse.user == null || profileResponse.user.name.isNullOrEmpty()){
            startActivity(Intent(this, ProfileActivity::class.java))
        } else if(SharedPreferenceManager.getCurrentCommunityId() == -1){
            startActivity(Intent(this, CommunityActivity::class.java))
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        finish()
    }

}