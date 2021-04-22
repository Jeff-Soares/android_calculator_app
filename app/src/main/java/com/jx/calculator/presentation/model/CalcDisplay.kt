package com.jx.calculator.presentation.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.jx.calculator.util.set

data class CalcDisplay(
    val calc: ObservableField<Pair<String, Mode>> = ObservableField(),
    val result: ObservableField<String> = ObservableField()
) : BaseObservable() {

    fun setDigit(d: String) {
        calc.set(d, Mode.DIGIT)
    }

    fun setText(t: String) {
        calc.set(t, Mode.TEXT)
    }

    fun reset() {
        calc.set("", Mode.RESET)
        result.set("")
    }

    enum class Mode {
        DIGIT, TEXT, RESET, DONE
    }

}