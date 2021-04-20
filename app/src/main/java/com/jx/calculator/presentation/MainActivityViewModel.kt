package com.jx.calculator.presentation

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.jx.calculator.presentation.model.CalcDisplay

class MainActivityViewModel : ViewModel() {
    val display = CalcDisplay()

    fun setDigit(v: View) {
        display.setDigit((v as? Button)?.run { text.toString() } ?: "")
    }

}

@BindingAdapter("bindText")
fun setText(view: EditText, observable: ObservableField<String>) {
    view.text.insert(view.selectionStart, observable.get() ?: "")
}

