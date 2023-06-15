package com.abhishekbharti.lounge.common

import android.content.Context
import android.net.ConnectivityManager
import java.net.InetAddress


object CommonUtil {

//    fun isNetworkConnected(context: Context): Boolean {
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected && isNetworkConnected(context)
//    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr = InetAddress.getByName("google.com")
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }
}