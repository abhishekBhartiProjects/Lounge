package com.abhishekbharti.lounge.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.common.SharedPreferenceManager
import com.abhishekbharti.lounge.databinding.HomeActivityBinding
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.response.FeedPostResponse
import org.jetbrains.annotations.Nullable
import java.util.Locale
import java.util.Objects

class HomeActivity : AppCompatActivity() {

    private lateinit var mBinding: HomeActivityBinding
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeAdapter? = null
    private val REQUEST_CODE_SPEECH_INPUT = 1
    private var mFeedPostResponse: FeedPostResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initViewModel()
        initView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFeedPosts(SharedPreferenceManager.getCurrentCommunityId(), 1)
    }

    private fun initViewModel(){
        viewModel.feedPostResponseMLD.observe(this){
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    onGetFeedPostResponseSuccess(it.data as FeedPostResponse)
                }
                else -> {}
            }
        }
    }
    private fun initView() {
        initAdapter()
    }

    private fun initAdapter(){
        mBinding.apply {
            adapter = HomeAdapter(viewModel)

            feedListRv.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, true)
            feedListRv.adapter = adapter
        }
    }

    private fun startAudioRecording(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            Toast
                .makeText(this, " " + e.message,
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                val prompt = Objects.requireNonNull<ArrayList<String>?>(result)[0]
//                viewModel.postPrompt(prompt)
                viewModel.generateImage(prompt)
            }
        }
    }

    private fun onGetFeedPostResponseSuccess(response: FeedPostResponse){
        mFeedPostResponse = response
        adapter?.submitList(response.post)
    }
}