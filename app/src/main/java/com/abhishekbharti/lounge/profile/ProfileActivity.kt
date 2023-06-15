package com.abhishekbharti.lounge.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.HomeActivityBinding
import com.abhishekbharti.lounge.databinding.ProfileActivityBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ProfileActivityBinding

    companion object{
        var enableEdit = true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if(enableEdit){
            setEditProfileFragment()
        } else {
            setProfileFragment()
        }
    }

    private fun setEditProfileFragment(){
        val frag = EditProfileFragment.newInstance()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.replace(R.id.fragmentContainer, frag, "EditProfileFragment")
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun setProfileFragment(){
        val frag = ProfileFragment.newInstance()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.replace(R.id.fragmentContainer, frag, "ProfileFragment")
        fragmentTransaction.commitAllowingStateLoss()
    }
}