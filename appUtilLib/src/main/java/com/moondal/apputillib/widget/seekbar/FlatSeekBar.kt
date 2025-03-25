package com.moondal.apputillib.widget.seekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.moondal.apputillib.R
import kotlin.math.roundToInt

/**
 *
 * Created on 2025. 2. 25..
 */
open class FlatSeekBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    interface OnSeekBarChangeListener {
        fun onProgressChanged(seekBar: FlatSeekBar, progress: Int, fromUser: Boolean) {}
        fun onScaledProgressChanged(seekBar: FlatSeekBar, scaledProgress: Int, fromUser: Boolean) {}
        fun onStartTrackingTouch(seekBar: FlatSeekBar) {}
        fun onStopTrackingTouch(seekBar: FlatSeekBar) {}
    }

    protected var mMin = 0
    protected var mMax = 100
    protected var mProgress = 0
    protected var backgroundPaint: Paint
    protected var progressPaint: Paint
    private var listener: OnSeekBarChangeListener? = null
    private var isTrackingTouch = false // 터치 중 여부
    private var touchStartX = 0f // 터치 시작 x 좌표 저장

    var scaleFactor: Int = 1        // value 에 곱할 값


    var min: Int
        get() = mMin
        set(value) {
            if (mMin != value) {
                mMin = value
                // max 값보다 작거나 같도록 보장
                if (mMax < mMin) {
                    mMax = mMin
                }
                // progress 값이 유효 범위 내에 있도록 보장
                progress = mProgress // setter를 통해 값 보정
                invalidate()
            }
        }

    var max: Int
        get() = mMax
        set(value) {
            if (mMax != value) {
                mMax = value
                invalidate()
                onProgressChanged(mProgress, false)
            }
        }

    var progress: Int
        get() = mProgress
        set(value) {
            val coercedProgress = value.coerceIn(mMin, max)
            if (mProgress != coercedProgress) {
                mProgress = coercedProgress
                invalidate()
                onProgressChanged(mProgress, false)
            }
        }

    protected open fun onProgressChanged(progress: Int, fromUser: Boolean) {
        listener?.onProgressChanged(this, progress, fromUser)
        listener?.onScaledProgressChanged(this, progress * scaleFactor, fromUser)
    }

    protected open fun onStartTrackingTouch() {
//        parent.requestDisallowInterceptTouchEvent(true)   // onTouchEvent 쪽에서 제어하는 것으로 바꿈
        listener?.onStartTrackingTouch(this)
    }

    protected open fun onStopTrackingTouch() {
//        parent.requestDisallowInterceptTouchEvent(false)  // onTouchEvent 쪽에서 제어하는 것으로 바꿈
        listener?.onStopTrackingTouch(this)
    }

    init {
        // 중요!! TypedArray obtainStyledAttributes 의 use 사용하면 OS 11 이하에서 크래쉬 발생. Caused by java.lang.IncompatibleClassChangeError: Class 'android.content.res.TypedArray' does not implement interface 'java.lang.AutoCloseable' in call to 'void java.lang.AutoCloseable.close()' (declaration of 'S7.J' appears in base.apk!classes3.dex) at kotlin.jdk7.AutoCloseableKt.closeFinally(AutoCloseable.kt:42)
        // 이전에 사용해된 크래시 발생하던 코드 : context.theme.obtainStyledAttributes(attrs, R.styleable.FlatSeekBar, 0, 0).use {
        context.theme.obtainStyledAttributes(attrs, R.styleable.FlatSeekBar, 0, 0).apply {
            try {
                mMin = getInt(R.styleable.FlatSeekBar_min, 0)
                mMax = getInt(R.styleable.FlatSeekBar_max, 100)
                mProgress = getInt(R.styleable.FlatSeekBar_progress, 0)
                val backgroundColor = getColor(R.styleable.FlatSeekBar_backgroundColor, Color.LTGRAY)
                val progressColor = getColor(R.styleable.FlatSeekBar_progressColor, Color.GRAY)

                backgroundPaint = Paint().apply {
                    style = Paint.Style.FILL
                    color = backgroundColor
                    isAntiAlias = true  // 그림을 그릴 때 계단 현상(aliasing)을 부드럽게 처리하려면 true 사용.
                }
                progressPaint = Paint().apply {
                    style = Paint.Style.FILL
                    color = progressColor
                    isAntiAlias = true
                }
            } finally {
                recycle()
            }
        }
        progress = mProgress
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val availableWidth = width.toFloat()
        val progressWidth = availableWidth * ((progress - mMin).toFloat() / (max - mMin))
        canvas.drawRect(0f, 0f, progressWidth, height.toFloat(), progressPaint)
        canvas.drawRect(progressWidth, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
    }

    //    private var touchStartX = 0f // 터치 시작 x 좌표 저장
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStartX = event.x // 터치 시작 x 좌표 저장
                true // ACTION_MOVE 이벤트 처리를 위해 true 반환
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.x - touchStartX // x 좌표 변화량 계산
                val dragThreshold = 20f // 드래그 감지 최소 이동 거리

                if (!isTrackingTouch && Math.abs(deltaX) > dragThreshold) {
                    // 드래그 시작
                    parent.requestDisallowInterceptTouchEvent(true)
                    isTrackingTouch = true
                    onStartTrackingTouch()
                    updateProgress(event.x)
                    true
                }

                if (isTrackingTouch) {
                    updateProgress(event.x)
                    true
                }
                false
            }

            MotionEvent.ACTION_UP -> {
                if (isTrackingTouch) {
                    isTrackingTouch = false
                    onStopTrackingTouch()
                    parent.requestDisallowInterceptTouchEvent(false)
                    true
                } else {
                    onStartTrackingTouch() // 탭 시 onStartTrackingTouch 호출
                    updateProgress(event.x) // 탭한 위치로 progress 업데이트
                    onStopTrackingTouch() // 탭 시 onStopTrackingTouch 호출
                }
                false
            }

            MotionEvent.ACTION_CANCEL -> {
                if (isTrackingTouch) {
                    isTrackingTouch = false
                    onStopTrackingTouch()
                    parent.requestDisallowInterceptTouchEvent(false)
                    true
                }
                false
            }

            else -> super.onTouchEvent(event)
        }
    }

    private fun updateProgress(x: Float) {
        val availableWidth = width.toFloat() // 사용 가능한 width
        val newProgress = ((x / availableWidth) * (mMax - mMin) + mMin).roundToInt() // min 값 고려
        val coercedProgress = newProgress.coerceIn(mMin, mMax)
        if (coercedProgress != mProgress) { // 값이 변경된 경우에만 이벤트 호출
            mProgress = coercedProgress
            invalidate() // 뷰 다시 그리기
            onProgressChanged(mProgress, true)
        }
    }

    fun setOnSeekBarChangeListener(listener: OnSeekBarChangeListener) {
        this.listener = listener
    }

    fun setInitValue(min: Int, max: Int, progress: Int, scaleFactor: Int = 1) {
        mMin = min
        mMax = max
        this.progress = progress
        this.scaleFactor = scaleFactor
        invalidate()
    }

    fun setInitColor(backgroundColor: Int, progressColor: Int) {
        backgroundPaint.color = backgroundColor
        progressPaint.color = progressColor
        invalidate()
    }
}