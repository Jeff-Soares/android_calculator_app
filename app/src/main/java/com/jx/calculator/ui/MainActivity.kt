package com.jx.calculator.ui

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.jx.calculator.R
import com.jx.calculator.databinding.ActivityMainBinding
import com.jx.calculator.presentation.MainActivityViewModel
import com.jx.calculator.util.doOnAnimationEnd
import com.jx.calculator.util.startRipple

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewModel = viewModel

        setupDisplay()
        setupBackspaceBtn()
        setupEqualBtn()
        setupRotateBtn()
    }

    private fun setupDisplay() {
        binding.displayCalc.showSoftInputOnFocus = false
        binding.displayCalc.doAfterTextChanged { text -> viewModel.calculate(text.toString()) }
        binding.displayCalc.requestFocus()
    }

    private fun setupBackspaceBtn() {
        binding.btnBackspace.setOnClickListener {
            with(binding.displayCalc) {
                if (text.isNullOrBlank()) return@setOnClickListener
                if (selectionEnd - selectionStart > 0) {
                    text!!.delete(selectionStart, selectionEnd)
                } else if (selectionStart - 1 >= 0) {
                    text!!.delete(selectionStart - 1, selectionEnd)
                }
            }
        }
        binding.btnBackspace.setOnLongClickListener {
            binding.displayLayout.apply {
                drawableHotspotChanged(width.toFloat(), height.toFloat())
                startRipple()
            }
            viewModel.display.reset()
            false
        }
    }

    private fun setupEqualBtn() {
        binding.btnEqual.setOnClickListener {
            val slideUpCalc: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
            val slideUpResult: Animation =
                AnimationUtils.loadAnimation(this, R.anim.slide_up_and_scale)

            slideUpResult.doOnAnimationEnd(viewModel::setResultOfCalc)

            binding.displayResult.startAnimation(slideUpResult)
            binding.displayCalc.startAnimation(slideUpCalc)
        }
    }

    private fun setupRotateBtn() {
        binding.btnRotate.setOnClickListener {
            requestedOrientation =
                if (requestedOrientation == Configuration.ORIENTATION_PORTRAIT)
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                else
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

}
