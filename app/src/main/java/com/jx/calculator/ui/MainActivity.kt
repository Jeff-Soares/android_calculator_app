package com.jx.calculator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.jx.calculator.databinding.ActivityMainBinding
import com.jx.calculator.presentation.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.display = viewModel.display
        binding.viewModel = viewModel

        binding.displayCalc.showSoftInputOnFocus = false
        binding.displayCalc.requestFocus()

        binding.backspace.setOnClickListener {
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
