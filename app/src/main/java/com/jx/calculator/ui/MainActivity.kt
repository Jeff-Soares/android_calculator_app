package com.jx.calculator.ui

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
    }

    private fun setupEqualBtn() {
        binding.btnEqual.setOnClickListener {
            val slideUpCalc: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
            val slideUpResult: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)

            slideUpResult.doOnAnimationEnd(viewModel::setResultOfCalc)

            binding.displayResult.startAnimation(slideUpResult)
            binding.displayCalc.startAnimation(slideUpCalc)
        }
    }

}
