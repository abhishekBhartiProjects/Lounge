package com.abhishekbharti.lounge.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import com.abhishekbharti.lounge.R

class SlideViewLayout : ConstraintLayout {

    var startOffset: Long = 50
    var intervalOffset: Long = 50


    companion object {
        const val TAG = "SlideViewLayout"
        const val HIDE = "hide"
        var animDuration: Long = 300
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context) : super(context) {
        initView(null)
    }

    fun initView(attrs: AttributeSet? = null) {
        if (attrs != null) {
            val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.SlideViewLayout, 0, 0)

            try {
                startOffset = attributes.getInt(R.styleable.SlideViewLayout_animStartOffset, 50).toLong()
                intervalOffset = attributes.getInt(R.styleable.SlideViewLayout_animIntervalOffset, 50).toLong()
                animDuration = attributes.getInt(R.styleable.SlideViewLayout_animDuration, 500).toLong()
            } finally {
                attributes.recycle()
            }
        }
    }

    fun enterRightToLeft() {
        var mOffset: Long = startOffset
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val animation = AnimationUtils.loadAnimation(context, R.anim.enter_right_to_left)
            animation.startOffset = mOffset
            animation.duration = animDuration
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {
                    if(view.tag != null && view.tag is String && view.tag.equals(HIDE)) {
                        view.visibility = View.GONE
                    } else {
                        view.visibility = View.VISIBLE
                    }
                }

                override fun onAnimationEnd(animation: Animation?) {

                }

            })
            view.startAnimation(animation)
            mOffset += intervalOffset
            if (visibility == View.GONE) {
                visibility = View.VISIBLE
            }
        }
    }

    fun enterLeftToRight() {
        var mOffset: Long = startOffset
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val animation = AnimationUtils.loadAnimation(context, R.anim.enter_left_to_right)
            animation.startOffset = mOffset
            animation.duration = animDuration
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {
                    if(view.tag != null && view.tag is String && view.tag.equals(HIDE)) {
                        view.visibility = View.GONE
                    } else {
                        view.visibility = View.VISIBLE
                    }
                }

                override fun onAnimationEnd(animation: Animation?) {

                }

            })
            view.startAnimation(animation)
            mOffset += intervalOffset
            if (visibility == View.GONE) {
                visibility = View.VISIBLE
            }
        }
    }

    fun exitRightToLeft() {
        var mOffset: Long = startOffset
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val animation = AnimationUtils.loadAnimation(context, R.anim.exit_right_to_left)
            animation.startOffset = mOffset
            animation.duration = animDuration
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    if(view.tag != null && view.tag is String && view.tag.equals(HIDE)) {
                        view.visibility = View.GONE
                    } else {
                        view.visibility = View.INVISIBLE
                    }

                    if (i == childCount - 1 && visibility == View.VISIBLE) {
                        visibility = View.GONE
                    }
                }

            })
            view.startAnimation(animation)
            mOffset += intervalOffset
        }
    }

    fun exitLeftToRight() {
        var mOffset: Long = startOffset
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val animation = AnimationUtils.loadAnimation(context, R.anim.exit_left_to_right)
            animation.startOffset = mOffset
            animation.duration = animDuration
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    if(view.tag != null && view.tag is String && view.tag.equals(HIDE)) {
                        view.visibility = View.GONE
                    } else {
                        view.visibility = View.INVISIBLE
                    }
                    if (i == childCount - 1 && visibility == View.VISIBLE) {
                        visibility = View.GONE
                    }
                }

            })

            view.startAnimation(animation)
            mOffset += intervalOffset
        }
    }

}