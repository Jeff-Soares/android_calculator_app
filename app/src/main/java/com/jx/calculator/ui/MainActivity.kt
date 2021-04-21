package com.jx.calculator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.jx.calculator.databinding.ActivityMainBinding
import com.jx.calculator.presentation.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.display = viewModel.display
        binding.viewModel = viewModel

        binding.displayCalc.showSoftInputOnFocus = false
        binding.displayCalc.requestFocus()

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

}
