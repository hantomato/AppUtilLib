package com.moondal.apputilsample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.core.graphics.withClip
import com.moondal.apputillib.widget.seekbar.LabelSeekBar

/**
 *
 * 설정을 하드코딩해서 사용할 용도
 *
 * Created by neowiztomato on 2025. 3. 17..
 */
class MySeekBar(context: Context, attrs: AttributeSet?) : LabelSeekBar(context, attrs) {

    init {

        radius = 30f
        startPadding = 30f
        endPadding = 30f

        backgroundPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#ff8822")
            isAntiAlias = true  // 그림을 그릴 때 계단 현상(aliasing)을 부드럽게 처리하려면 true 사용.
        }

        progressPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#ff0022")
            isAntiAlias = true
        }

        textPaint = Paint().apply {
            color = Color.parseColor("#550022")
            textSize = 56f
            textAlign = Paint.Align.LEFT
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        val roundRectClip = Path().apply {
            val radius = radius
            addRoundRect(0f, 0f, width.toFloat(), height.toFloat(), radius, radius, Path.Direction.CW)
        }
        canvas.withClip(roundRectClip) {
            val availableWidth = width.toFloat()
            val progressWidth = availableWidth * ((progress - mMin).toFloat() / (max - mMin))
            canvas.drawRect(0f, 0f, progressWidth, height.toFloat(), progressPaint)
            canvas.drawRect(progressWidth, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

            // draw label text
            textPaint.textAlign = Paint.Align.LEFT
            val labelTextX = 0 + startPadding // 텍스트 X 좌표 (오른쪽 정렬)
            val labelTextY = height / 2f - ((textPaint.descent() + textPaint.ascent()) / 2) // 텍스트 Y 좌표 (중앙 정렬)
            drawText(leftLabelText, labelTextX + 80, labelTextY, textPaint)

            // draw value text
            textPaint.textAlign = Paint.Align.RIGHT
            val valueText = (mProgress * scaleFactor).toString()
            val valueTextX = width - endPadding // 텍스트 X 좌표 (오른쪽 정렬)
            val valueTextY = height / 2f - ((textPaint.descent() + textPaint.ascent()) / 2) // 텍스트 Y 좌표 (중앙 정렬)
            drawText(valueText, valueTextX, valueTextY, textPaint)
        }
    }


}