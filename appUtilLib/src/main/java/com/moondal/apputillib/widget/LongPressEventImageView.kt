package com.moondal.apputillib.widget

import android.content.Context
import android.graphics.PointF
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.sqrt

/**
 *
 * Created on 2025. 4. 13..
 */
class LongPressEventImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    private var continuousActionStarted = false
    private var isFingerExitedView = false  // down 시에 false, 손가락이 View 영역을 벗어나면 true.
    private val downPt = PointF(0f, 0f)
    private val moveThreshold = 20f // 움직임 임계값 (픽셀)

    private var mPressEventListener: (() -> Unit)? = null
    private val mHandler = Handler(Looper.getMainLooper())
    private val continuousActionRunnable: Runnable = object : Runnable {
        override fun run() {
            mPressEventListener?.invoke()
            mHandler.postDelayed(this, continuousActionDelay)
        }
    }

    var continuousActionStartDelay = 300L
    var continuousActionDelay = 70L

    init {
        setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downPt.set(event.x, event.y)
                    continuousActionStarted = false
                    isFingerExitedView = false
                    mHandler.postDelayed({
                        continuousActionStarted = true
                        mHandler.post(continuousActionRunnable)
                    }, continuousActionStartDelay)
                }

                MotionEvent.ACTION_MOVE -> {
                    val distance =
                        sqrt((event.x - downPt.x) * (event.x - downPt.x) + (event.y - downPt.y) * (event.y - downPt.y))
                    if (distance > moveThreshold) {
                        mHandler.removeCallbacksAndMessages(null)    // 모든 콜백 및 메시지 제거
                    }

                    if (!isFingerExitedView && !isTouchInsideView(event)) {  // view 영역 벗어난 경우에는 클릭 무시되게 처리하기 위함.
                        isFingerExitedView = true
                    }
                }

                MotionEvent.ACTION_UP -> {
                    mHandler.removeCallbacksAndMessages(null)    // 모든 콜백 및 메시지 제거

                    // 손가락이 View 영역 벗어난적도 없고, continuousAction 도 처리된적 없다면. 클릭 액션 처리
                    if (!isFingerExitedView) {
                        if (!continuousActionStarted) {
                            mPressEventListener?.invoke()
                        }
                        performClick()
                    }
                }

                MotionEvent.ACTION_CANCEL -> {
                    mHandler.removeCallbacksAndMessages(null)    // 모든 콜백 및 메시지 제거
                }
            }
            true
        }
    }

    private fun isTouchInsideView(event: MotionEvent): Boolean {
        return event.x >= 0 && event.x <= width && event.y >= 0 && event.y <= height
    }

    fun setPressEventListener(listener: (() -> Unit)?) {
        mPressEventListener = listener
    }
}