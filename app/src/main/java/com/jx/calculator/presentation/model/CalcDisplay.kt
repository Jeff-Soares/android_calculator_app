package com.jx.calculator.presentation.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

data class CalcDisplay(
    val calc: ObservableField<String> = ObservableField(),
    val result: ObservableField<String> = ObservableField()
) : BaseObservable() {

    fun setDigit(str: String) {
        calc.set(str)
        notifyChange()
    }

    fun setResult(r: String) {
        result.set(r)
        notifyChange()
    }

    fun resetCalc() {
        calc.set("")
        notifyChange()
    }

    fun resetAll() {
        calc.set("")
        result.set("")
        notifyChange()
    }
}