package com.abhishekbharti.lounge.login

import com.abhishekbharti.lounge.base.BaseRepo
import com.abhishekbharti.lounge.common.PhoneNumberUtils
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.requestBody.SendOtpRequestBody
import com.abhishekbharti.lounge.requestBody.VerifyOtpRequestBody
import com.abhishekbharti.lounge.response.ProfileResponse
import com.abhishekbharti.lounge.response.SendOtpResponse
import com.abhishekbharti.lounge.response.VerifyOtpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepo: BaseRepo() {

    suspend fun sendOtp(phoneNo: String, countryCode: String): RequestResult<SendOtpResponse?> {
        return withContext(Dispatchers.IO) {
            val formattedNumber = PhoneNumberUtils.getPseudoValidPhoneNumber(phoneNo, countryCode)
            val sendOtpRequestBody = SendOtpRequestBody().apply {
                phone = formattedNumber
            }
            return@withContext handleCommonResponse(
                {
                    apiInterfaceWithoutToken.sendMobileVerificationOtp(sendOtpRequestBody)
                },
                { RequestResult.Success(it.body()) }
            )

        }
    }

    suspend fun verifyOtp(
        phoneNo: String,
        otpString: String
    ): RequestResult<VerifyOtpResponse?> {
        return withContext(Dispatchers.IO) {
            return@withContext handleCommonResponse(
                {
                    val requestBody = VerifyOtpRequestBody().apply {
                        phone = phoneNo
                        otp = otpString
                    }
                    apiInterfaceWithoutToken.verifyMobileVerificationOtp(requestBody)
                }, {
                    RequestResult.Success(it.body())
                }
            )
        }
    }

    suspend fun getProfile(
    ): RequestResult<ProfileResponse?> {
        return withContext(Dispatchers.IO) {
            return@withContext handleCommonResponse(
                {
                    apiInterface.getProfile()
                }, {
                    RequestResult.Success(it.body())
                }
            )
        }
    }
}