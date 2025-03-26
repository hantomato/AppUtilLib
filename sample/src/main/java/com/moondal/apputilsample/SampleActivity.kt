package com.moondal.apputilsample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.moondal.apputillib.widget.seekbar.FlatSeekBar
import com.moondal.apputilsample.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {
    private val TAG = "SampleActivity" // 액티비티 태그
    private val binding by lazy { ActivitySampleBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        initialize()
    }

    private fun initialize() {

        binding.flatSeekBar1.setOnSeekBarChangeListener(object : FlatSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: FlatSeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "flatSeekBar1.onProgressChanged: progress:$progress, fromUser:$fromUser")
            }

        })

        binding.flatSeekBar2.setInitValue(-30, 70, 20)
        binding.flatSeekBar2.setInitColor(Color.parseColor("#F5EEDC"), Color.parseColor("#DDA853"))
        binding.flatSeekBar2.setOnSeekBarChangeListener(object : FlatSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: FlatSeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "flatSeekBar2.onProgressChanged: progress:$progress, fromUser:$fromUser")
            }

            override fun onStartTrackingTouch(seekBar: FlatSeekBar) {
                Log.d(TAG, "flatSeekBar2.onStartTrackingTouch:")
            }

            override fun onStopTrackingTouch(seekBar: FlatSeekBar) {
                Log.d(TAG, "flatSeekBar2.onStopTrackingTouch:")
            }

        })

        binding.flatSeekBar3.setOnSeekBarChangeListener(object : FlatSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: FlatSeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "flatSeekBar3.onProgressChanged: progress:$progress, fromUser:$fromUser")
            }

            override fun onStartTrackingTouch(seekBar: FlatSeekBar) {
                Log.d(TAG, "flatSeekBar3.onStartTrackingTouch:")
            }

            override fun onStopTrackingTouch(seekBar: FlatSeekBar) {
                Log.d(TAG, "flatSeekBar3.onStopTrackingTouch:")
            }

        })

        binding.transparentSeekBar.setOnSeekBarChangeListener(object : FlatSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: FlatSeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "transparentSeekBar.onProgressChanged: progress:$progress, fromUser:$fromUser")
            }

        })

        binding.labelSeekBar1.setOnSeekBarChangeListener(object : FlatSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: FlatSeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "labelSeekBar1.onProgressChanged: progress:$progress, fromUser:$fromUser")
            }

            override fun onScaledProgressChanged(seekBar: FlatSeekBar, scaledProgress: Int, fromUser: Boolean) {
                Log.d(TAG, "labelSeekBar1.onProgressChanged: scaledProgress:$scaledProgress, fromUser:$fromUser")
            }

            override fun onStartTrackingTouch(seekBar: FlatSeekBar) {
                Log.d(TAG, "labelSeekBar1.onStartTrackingTouch:")
            }

            override fun onStopTrackingTouch(seekBar: FlatSeekBar) {
                Log.d(TAG, "labelSeekBar1.onStopTrackingTouch:")
            }

        })

        binding.labelSeekBar2.setInitValue(-100, 200, 0, 10)
        binding.labelSeekBar2.setLabelText("Text Size")
        binding.labelSeekBar2.setInitColor(Color.parseColor("#E4EFE7"), Color.parseColor("#99BC85"))
        binding.labelSeekBar2.setOnSeekBarChangeListener(object : FlatSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: FlatSeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "labelSeekBar2.onProgressChanged: progress:$progress, fromUser:$fromUser")
            }

            override fun onScaledProgressChanged(seekBar: FlatSeekBar, scaledProgress: Int, fromUser: Boolean) {
                Log.d(TAG, "labelSeekBar2.onProgressChanged: scaledProgress:$scaledProgress, fromUser:$fromUser")
            }

        })

        binding.labelSeekBar3.setInitValue(0, Int.MAX_VALUE, 0)
        binding.labelSeekBar3.setLabelInitValue("Text Size", Color.parseColor("#4444FF"), 40f, true)
        binding.labelSeekBar3.setInitColor(Color.parseColor("#E4EFE7"), Color.parseColor("#99BC85"))
        binding.labelSeekBar3.setOnSeekBarChangeListener(object : FlatSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: FlatSeekBar, progress: Int, fromUser: Boolean) {
                binding.labelSeekBar3.setLabelColor(progress)
                binding.labelSeekBar3.setLabelText("Size: " + progress.toString())
            }

        })

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "seekbar.onProgressChanged: progress:$progress, fromUser:$fromUser")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                Log.d(TAG, "seekbar.onStartTrackingTouch:")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Log.d(TAG, "seekbar.onStopTrackingTouch:")
            }

        })

    }
}