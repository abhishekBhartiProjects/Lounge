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
import com.abhishekbharti.lounge.community.CommunitySuggestionBottomsheet
import com.abhishekbharti.lounge.databinding.HomeActivityBinding
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.qam.QamActivity
import com.abhishekbharti.lounge.qam.WebviewActivity
import com.abhishekbharti.lounge.response.CompletionResponse
import com.abhishekbharti.lounge.response.FeedPostResponse
import com.abhishekbharti.lounge.response.GetCommunityDetailResponse
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
        initClickListeners()
    }

    private fun initClickListeners(){
        mBinding.apply {
            currentCummunityNameTv.setOnClickListener {
                val communityDialog = CommunitySuggestionBottomsheet.newInstance(Bundle())

                if (!isFinishing) {
                    communityDialog.show(supportFragmentManager, "CommunitySuggestionBottomsheet")
                }
            }

            membersTv.setOnClickListener {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCommunityDetails(SharedPreferenceManager.getCurrentCommunityId())
        viewModel.getFeedPosts(SharedPreferenceManager.getCurrentCommunityId(), 1)
    }

    private fun initViewModel(){
        viewModel.communityDetailResponseMLD.observe(this){
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    val qamList = (it.data as GetCommunityDetailResponse).community_qams
                    mBinding.apply {
                        qam1Tv.text = qamList.get(0).title
                        qam2Tv.text = qamList.get(1).title

                        qamCreateTv.setOnClickListener {
                            startActivity(Intent(this@HomeActivity, QamActivity::class.java))
                        }

                        qam1Tv.setOnClickListener {
                            Toast.makeText(this@HomeActivity, "Open in webview", Toast.LENGTH_SHORT).show()
                            val bundle = Bundle()
                            bundle.putString("url", qamList.get(0).link)
                            val intent = Intent(this@HomeActivity, WebviewActivity::class.java)
                            intent.putExtras(bundle)

                            startActivity(intent)

                        }
                        qam2Tv.setOnClickListener {
                            Toast.makeText(this@HomeActivity, "Open in webview", Toast.LENGTH_SHORT).show()
                            val bundle = Bundle()
                            bundle.putString("url", qamList.get(1).link)
                            val intent = Intent(this@HomeActivity, WebviewActivity::class.java)
                            intent.putExtras(bundle)

                            startActivity(intent)
                        }

                        qam3Tv.setOnClickListener {
                            viewModel.postPrompt("My name is Abhishek, Can you write an email to apply sick leave for 2 days to my manager Vikas")
                        }
                    }
                }
                else -> {}
            }
        }

        viewModel.promptResponseMLD.observe(this){
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    val data = (it.data as CompletionResponse)
                    val res = data.choices.get(0).text

                    Toast.makeText(this, res, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

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