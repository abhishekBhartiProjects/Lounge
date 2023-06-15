package com.abhishekbharti.lounge.common

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.Transformation
import com.abhishekbharti.lounge.R

object AnimationUtil {

    var ANIM_DURATION_SMALL = 100
    var ANIM_DURATION_NORMAL = 300
    var ANIM_DURATION_LARGE = 500

    fun setButtonAnimation(view: View, fromX: Float, toY: Float, duration: Long) {
        val animationSet = AnimationSet(true)

        val growAnimation = ScaleAnimation(
            fromX,
            toY,
            fromX,
            toY,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        val shrinkAnimation = ScaleAnimation(
            toY,
            fromX,
            toY,
            fromX,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

        animationSet.addAnimation(growAnimation)
        animationSet.addAnimation(shrinkAnimation)
        animationSet.duration = duration

        view.startAnimation(animationSet)
    }

    fun expandView(
        v: View, listenerExpand: Animation.AnimationListener?, duration: Int
    ) {
        v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetWidth = v.measuredWidth
        v.layoutParams.width = 1
        v.visibility = View.VISIBLE
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.width =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetWidth * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        a.duration = (
                (targetWidth / v.context.resources.displayMetrics.density).toInt()
                        * duration).toLong()
        a.interpolator = DecelerateInterpolator()
        a.setAnimationListener(listenerExpand)
        v.startAnimation(a)
    }

    fun collapseView(
        v: View, listenerCollapse: Animation.AnimationListener?, duration: Int
    ) {
        val initialWidth = v.measuredWidth
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.width =
                        initialWidth - (initialWidth * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = ANIM_DURATION_NORMAL.toLong()
        a.interpolator = DecelerateInterpolator()
        a.setAnimationListener(listenerCollapse)
        v.startAnimation(a)
    }

    var startOffset: Long = 50
    var intervalOffset: Long = 50
    var animDuration: Long = 500

    fun fadeIn(view: View) {
        var mOffset: Long = startOffset
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.fade_in)
        animation.startOffset = mOffset
        animation.duration = animDuration
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {
                if(view.tag != null && view.tag is String && view.tag.equals("hide")) {
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
        if (view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        }
    }

    fun fadeOut(view: View) {
        var mOffset: Long = startOffset
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.fade_out)
        animation.startOffset = mOffset
        animation.duration = animDuration
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                if(view.tag != null && view.tag is String && view.tag.equals("hide")) {
                    view.visibility = View.GONE
                } else {
                    view.visibility = View.INVISIBLE
                }

                if (view.visibility == View.VISIBLE) {
                    view.visibility = View.GONE
                }
            }

        })
        view.startAnimation(animation)
        mOffset += intervalOffset
    }

    fun slideUp(view: View) {
        var mOffset: Long = startOffset
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.slide_up)
        animation.startOffset = mOffset
        animation.duration = animDuration
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                if(view.tag != null && view.tag is String && view.tag.equals("hide")) {
                    view.visibility = View.GONE
                } else {
                    view.visibility = View.INVISIBLE
                }

                if (view.visibility == View.VISIBLE) {
                    view.visibility = View.GONE
                }
            }

        })
        view.startAnimation(animation)
        mOffset += intervalOffset
    }

    fun slideDown(view: View) {
        var mOffset: Long = startOffset
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.slide_down)
        animation.startOffset = mOffset
        animation.duration = animDuration
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                if(view.tag != null && view.tag is String && view.tag.equals("hide")) {
                    view.visibility = View.GONE
                } else {
                    view.visibility = View.INVISIBLE
                }

                if (view.visibility == View.VISIBLE) {
                    view.visibility = View.GONE
                }
            }

        })
        view.startAnimation(animation)
        mOffset += intervalOffset
    }

    fun fadeInOut(view: View) {
        var mOffset: Long = startOffset
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.fade_in_out)
        animation.startOffset = mOffset
        animation.duration = animDuration
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                if(view.tag != null && view.tag is String && view.tag.equals("hide")) {
                    view.visibility = View.GONE
                } else {
                    view.visibility = View.INVISIBLE
                }

                if (view.visibility == View.VISIBLE) {
                    view.visibility = View.GONE
                }
            }

        })
        view.startAnimation(animation)
        mOffset += intervalOffset
    }
}