package com.abhishekbharti.lounge.base

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.abhishekbharti.lounge.LoungeApplication
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object ImageManager {
    private val context = LoungeApplication.getInstance()

    fun loadImage(imageView: ImageView, imageUrl: String?) {
        val url = imageUrl ?: ""
        load(
            imageView,
            url,
            0,
            getRequestOptions(imageView.drawable),
            null
        )
    }

    private fun getRequestOptions(drawable: Drawable?): RequestOptions {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        requestOptions = requestOptions.placeholder(drawable)
        requestOptions = requestOptions.error(drawable)
        return requestOptions
    }

    private fun load(
        imageView: ImageView, url: String, placeHolderId: Int,
        defaultRequestOptions: RequestOptions, transformationRequestOptions: RequestOptions?
    ) {
        try {
            Glide.with(context).clear(imageView)
            var requestManager = Glide.with(context)
            requestManager = requestManager.setDefaultRequestOptions(defaultRequestOptions)
            var requestBuilder: RequestBuilder<Drawable>? = null
            if (url.isNotEmpty()) {
                requestBuilder = requestManager.load(url)
            }
            if (placeHolderId > 0) {
                requestBuilder = requestManager.load(placeHolderId)
            }
            if (requestBuilder != null) {
                if (transformationRequestOptions != null) {
                    requestBuilder = requestBuilder.apply(transformationRequestOptions)
                }
                requestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(imageView)
            } else {
                Glide.with(context)
                    .setDefaultRequestOptions(defaultRequestOptions)
                    .load(url)
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(imageView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}