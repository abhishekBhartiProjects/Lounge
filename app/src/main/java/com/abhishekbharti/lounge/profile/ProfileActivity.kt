package com.abhishekbharti.lounge.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.HomeActivityBinding
import com.abhishekbharti.lounge.databinding.ProfileActivityBinding
import com.abhishekbharti.lounge.home.HomeViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ProfileActivityBinding
    private var mProfileId: Int = -1

    companion object{
        var enableEdit = true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initExtras()
        initView()
    }

    private fun initExtras(){
        val bundle = intent.extras
        bundle?.let {
            if(it.containsKey("id")){
                mProfileId = it.getInt("id")
            }
        }

    }

    private fun initView(){
        if(enableEdit){
            setEditProfileFragment()
        } else {
            setProfileFragment(mProfileId)
        }
    }

    private fun setEditProfileFragment(){
        val bundle = Bundle()
        bundle.putString("source", "onboarding")
        val frag = EditProfileFragment.newInstance(bundle)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.replace(R.id.fragmentContainer, frag, "EditProfileFragment")
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun setProfileFragment(id: Int){
        val bundle = Bundle()
        bundle.putInt("id", id)
        val frag = ProfileFragment.newInstance(bundle)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.replace(R.id.fragmentContainer, frag, "ProfileFragment")
        fragmentTransaction.commitAllowingStateLoss()
    }
}