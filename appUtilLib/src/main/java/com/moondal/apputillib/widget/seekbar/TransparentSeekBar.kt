package com.moondal.apputillib.widget.seekbar

import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator

/**
 *
 * Created on 2025. 2. 25..
 */
class TransparentSeekBar(context: Context, attrs: AttributeSet?) : FlatSeekBar(context, attrs) {

    private val fadeOutDuration = 1000L
    private val fadeOutDelay = 2000L

    init {
        alpha = 0f

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        alpha = 0f
    }

    override fun onStartTrackingTouch() {
        super.onStartTrackingTouch()
        cancelAnimation()
        alpha = 1f
    }

    override fun onStopTrackingTouch() {
        super.onStopTrackingTouch()
        startFadeOutAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAnimation()
    }

    private fun startFadeOutAnimation() {
        this.animate()
            .alpha(0f)
            .setDuration(fadeOutDuration)
            .setStartDelay(fadeOutDelay)
            .setInterpolator(LinearInterpolator())
            .start()
    }

    private fun cancelAnimation() {
        this.animate().cancel()
    }
}