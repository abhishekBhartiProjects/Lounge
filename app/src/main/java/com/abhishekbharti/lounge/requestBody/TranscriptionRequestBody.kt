package com.abhishekbharti.lounge.requestBody

import java.io.File

data class TranscriptionRequestBody(
    val `file`: File,
    val model: String
)
