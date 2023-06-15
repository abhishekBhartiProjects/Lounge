package com.abhishekbharti.lounge.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.common.PhoneNumberUtils
import com.abhishekbharti.lounge.common.SharedPreferenceManager
import com.abhishekbharti.lounge.common.SlideViewLayout
import com.abhishekbharti.lounge.community.CommunityActivity
import com.abhishekbharti.lounge.databinding.LoginActivityBinding
import com.abhishekbharti.lounge.home.HomeActivity
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.profile.ProfileActivity
import com.abhishekbharti.lounge.response.ProfileResponse
import com.abhishekbharti.lounge.response.SendOtpResponse
import com.abhishekbharti.lounge.response.VerifyOtpResponse

class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding: LoginActivityBinding
    private val viewModel: LoginViewModel by viewModels()
    private var mCountryCode: String = "91"
    private var mIsPhoneValid: Boolean = false
    private var mPhoneTextWatcher: TextWatcher? = null
    private var mOtpTextWatcher: TextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initViewModelObserver()
        initViews()
        initClickListener()
    }

    private fun initViewModelObserver(){
        viewModel.sendOtpResponseMLD.observe(this) {
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    onSendOtpResponseSuccess(it.data as SendOtpResponse)
                }
                else ->{
                    Toast.makeText(this, "Network connection error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.verifyOtpResponseMLD.observe(this){
            when (it) {
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    onVerifyOtpResponseSuccess(it.data as VerifyOtpResponse)
                }
                else -> {
                    Toast.makeText(this, "Couldnot verify OTP", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getProfileResponseMLD.observe(this) {
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

    private fun initViews() {
        initTextWatcher()
        initCountryCode()
        initSendOtpButton()
    }

    private fun initTextWatcher() {
        mPhoneTextWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validatePhoneNumber(s)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        }
        mBinding?.phoneInputEt?.addTextChangedListener(mPhoneTextWatcher)

        mOtpTextWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mBinding?.apply {
                    invalidOtpTv.setText("")
                    invalidOtpTv.visibility = View.GONE
                    invalidOtpTv.tag = SlideViewLayout.HIDE
                    val len = s?.length ?: 0
                    if (len == 6) {
                        submitOtpButtonMcv.alpha = 1f
                        submitOtpButtonMcv.isEnabled = true
                        submitOtpButtonMcv.performClick()
                    } else {
                        submitOtpButtonMcv.alpha = 0.5f
                        submitOtpButtonMcv.isEnabled = false
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        }
    }

    private fun initCountryCode(){
        mBinding.apply {
            countryCodeTv.text = "+${mCountryCode}"
            flagTv.text = CountryCodeUtils().getCountryDetailsFromPhoneCode(mCountryCode).flag
        }
    }

    private fun initSendOtpButton() {
        mBinding?.apply {
            if (mIsPhoneValid) {
                sendOtpButtonMcv.alpha = 1f
                sendOtpButtonMcv.isEnabled = true
            } else {
                sendOtpButtonMcv.alpha = 0.5f
                sendOtpButtonMcv.isEnabled = false
            }
        }
    }

    private fun initClickListener(){
        mBinding.apply {
            sendOtpButtonMcv.setOnClickListener {
                sendVerificationCode()
            }

            submitOtpButtonMcv.setOnClickListener {
                val code = otpEt.text.toString()
                val phone = PhoneNumberUtils.getPseudoValidPhoneNumber(
                    phoneInputEt.text.toString(),
                    mCountryCode
                ) ?: ""

                viewModel.verifyOtp(phone, code)
            }
        }
    }

    private fun sendVerificationCode(){
        val tempPhoneNo = mBinding.phoneInputEt.text.toString()
        viewModel.sendOtp(tempPhoneNo, mCountryCode)
    }

    private fun validatePhoneNumber(phoneNum: CharSequence) {
        mBinding.apply {
            if (PhoneNumberUtils.isPhoneNumberValid(phoneNum.toString(), mCountryCode)) {
                mIsPhoneValid = true
                phoneInputEt.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_tick,
                    0
                )
                sendOtpButtonMcv.alpha = 1f
                sendOtpButtonMcv.isEnabled = true
            } else {
                mIsPhoneValid = false
                phoneInputEt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
                sendOtpButtonMcv.alpha = 0.5f
                sendOtpButtonMcv.isEnabled = false
            }
        }
    }

    private fun enableResendOtp(shouldEnable: Boolean) {
        if (shouldEnable) {
            mBinding?.apply {
                resendOtpTv.paint?.isUnderlineText = true
                resendOtpTv.isEnabled = true
                resendOtpTv.alpha = 1f
            }
        } else {
            mBinding?.apply {
                resendOtpTv.paint?.isUnderlineText = false
                resendOtpTv.isEnabled = false
                resendOtpTv.alpha = 0.5f
            }
        }
    }

    private fun onSendOtpResponseSuccess(sendOtpResponse: SendOtpResponse){
        initOtpScreen()
        slideTransitionRL(mBinding.loginSvl, mBinding.otpSvl)
    }

    private fun onVerifyOtpResponseSuccess(verifyOtpResponse: VerifyOtpResponse){
        if(verifyOtpResponse.token.isNotEmpty()){
            SharedPreferenceManager.setUserSessionToken(verifyOtpResponse.token)
            viewModel.getProfile()
        } else {
            Toast.makeText(this, "Invalid token!", Toast.LENGTH_LONG).show()
        }
    }

    private fun onGetProfileResponseSuccess(profileResponse: ProfileResponse){
        navigateToNextScreen(profileResponse)
    }

    private fun initOtpScreen() {
        mBinding.apply {
            val tempPhoneNo = phoneInputEt.text.toString()
            otpTitleTv.text = HtmlCompat.fromHtml(
                String.format(
                    getString(
                        R.string.otp_sent_to_x,
                        StringBuilder()
                            .append("+")
                            .append(mCountryCode)
                            .append(" ")
                            .append(tempPhoneNo)
                            .toString()
                    )
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            otpEt.requestFocus()

            submitOtpButtonMcv.isEnabled = false
            submitOtpButtonMcv.alpha = 0.5f

            enableResendOtp(false)
            invalidOtpTv.setText("")
            invalidOtpTv.visibility = View.GONE
            invalidOtpTv.tag = SlideViewLayout.HIDE

            otpEt.removeTextChangedListener(mOtpTextWatcher)
            otpEt.addTextChangedListener(mOtpTextWatcher)
        }
    }

    private fun slideTransitionRL(goingView: SlideViewLayout?, comingView: SlideViewLayout?) {
        goingView?.exitRightToLeft()
        Handler(Looper.getMainLooper()).postDelayed({
            comingView?.enterRightToLeft()
        }, SlideViewLayout.animDuration)
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