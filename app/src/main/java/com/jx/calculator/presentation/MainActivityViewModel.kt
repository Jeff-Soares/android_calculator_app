package com.jx.calculator.presentation

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.jx.calculator.presentation.model.CalcDisplay
import com.jx.calculator.presentation.model.CalcDisplay.Mode
import com.jx.calculator.presentation.model.CalcDisplay.Mode.*
import com.jx.calculator.util.removeTrailingZero
import com.jx.calculator.util.replaceSeparator
import com.jx.calculator.util.replaceOperators
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import java.lang.IllegalArgumentException

class MainActivityViewModel : ViewModel() {
    val display = CalcDisplay()

    fun setDigit(v: View) {
        display.setDigit((v as? Button)?.run { text.toString() } ?: "")
    }

    fun calculate(exp: String) {
        try {
            val calc = ExpressionBuilder(exp.replaceOperators()).build()
            val result = calc.evaluate().toString().removeTrailingZero()
            display.result.set(result.replaceSeparator())
        } catch (e: ArithmeticException) {
            display.result.set(e.message)
        } catch (e: IllegalArgumentException) {
            display.result.set("")
        } catch (e: Exception) {
            display.result.set("ERROR")
        }
    }

    fun setResultOfCalc() {
        val result = display.result.get()
        display.reset()
        display.setText(result ?: "")
    }

}

@BindingAdapter(value = ["bindCalc", "bindCalcAttrChanged"], requireAll = false)
fun setCalc(
    view: EditText,
    observable: ObservableField<Pair<String, Mode>>,
    listener: InverseBindingListener
) {
    val (str, mode) = observable.get() ?: Pair("", RESET)
    when (mode) {
        DIGIT -> view.text.insert(view.selectionStart, str)
        TEXT -> {
            view.setText(str)
            view.setSelection(view.length())
        }
        RESET -> view.setText("")
        DONE -> return
    }
    view.doAfterTextChanged { listener.onChange() }
}

@InverseBindingAdapter(attribute = "bindCalc", event = "bindCalcAttrChanged")
fun getCalc(view: EditText): Pair<String, Mode> {
    return Pair(view.text.toString(), DONE)
}
