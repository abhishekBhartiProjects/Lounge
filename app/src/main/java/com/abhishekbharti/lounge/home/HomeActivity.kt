package com.abhishekbharti.lounge.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.viewModels
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.databinding.HomeActivityBinding
import org.jetbrains.annotations.Nullable
import java.util.Locale
import java.util.Objects

class HomeActivity : AppCompatActivity() {

    private lateinit var mBinding: HomeActivityBinding
    private val viewModel: HomeViewModel by viewModels()
    private val REQUEST_CODE_SPEECH_INPUT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        initView()
    }

    private fun initView() {

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
}