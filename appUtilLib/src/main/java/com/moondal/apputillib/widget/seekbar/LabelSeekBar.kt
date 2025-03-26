package com.moondal.apputillib.widget.seekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.graphics.withClip
import com.moondal.apputillib.R

/**
 *
 * Created on 2025. 2. 25..
 */
open class LabelSeekBar(context: Context, attrs: AttributeSet?) : FlatSeekBar(context, attrs) {

    protected var leftLabelText: String = ""

    protected var radius = 0f
    protected var startPadding = 0f
    protected var endPadding = 0f
    protected var textPaint: Paint

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LabelSeekBar, 0, 0).apply {
            try {
                radius = getDimensionPixelSize(R.styleable.LabelSeekBar_radius, 30).toFloat()
                startPadding = getDimensionPixelSize(R.styleable.LabelSeekBar_startPadding, 30).toFloat()
                endPadding = getDimensionPixelSize(R.styleable.LabelSeekBar_endPadding, 30).toFloat()
                val textSizeTemp = getDimensionPixelSize(R.styleable.LabelSeekBar_textSize, 30)
                val textColorTemp = getColor(R.styleable.LabelSeekBar_textColor, Color.BLACK)

                textPaint = Paint().apply {
                    color = textColorTemp
                    textSize = textSizeTemp.toFloat()
                    textAlign = Paint.Align.LEFT
                    isAntiAlias = true
                }
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        val roundRectClip = Path().apply {
            val radius = radius
            addRoundRect(0f, 0f, width.toFloat(), height.toFloat(), radius, radius, Path.Direction.CW)
        }
        canvas.withClip(roundRectClip) {
            super.onDraw(this)

            // draw label text
            textPaint.textAlign = Paint.Align.LEFT
            val labelTextX = 0 + startPadding // 텍스트 X 좌표 (오른쪽 정렬)
            val labelTextY = height / 2f - ((textPaint.descent() + textPaint.ascent()) / 2) // 텍스트 Y 좌표 (중앙 정렬)
            drawText(leftLabelText, labelTextX, labelTextY, textPaint)

            // draw value text
            textPaint.textAlign = Paint.Align.RIGHT
            val valueText = (mProgress * scaleFactor).toString()
            val valueTextX = width - endPadding // 텍스트 X 좌표 (오른쪽 정렬)
            val valueTextY = height / 2f - ((textPaint.descent() + textPaint.ascent()) / 2) // 텍스트 Y 좌표 (중앙 정렬)
            drawText(valueText, valueTextX, valueTextY, textPaint)
        }
    }

    fun setLabelInitValue(text: String, textColor: Int, textSize: Float = 40f, textBold: Boolean = false) {
        leftLabelText = text
        textPaint.color = textColor
        textPaint.textSize = textSize
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, if (textBold) Typeface.BOLD else Typeface.NORMAL)
        invalidate()
    }

    fun setLabelText(labelText: String) {
        this.leftLabelText = labelText
        invalidate()
    }

    fun setLabelColor(labelColor: Int) {
        textPaint.color = labelColor
        invalidate()
    }

}