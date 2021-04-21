package com.jx.calculator.presentation

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.jx.calculator.presentation.model.CalcDisplay
import com.jx.calculator.presentation.model.TextListener
import com.jx.calculator.util.replaceOperators
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import java.lang.IllegalArgumentException

class MainActivityViewModel : ViewModel() {
    val display = CalcDisplay()

    fun setDigit(v: View) {
        display.setDigit((v as? Button)?.run { text.toString() } ?: "")
    }

    fun calculate() {
        val exp: String = display.calc.get() ?: ""
        if (exp.isBlank()) {
            display.result.set(""); return
        }
        try {
            val calc = ExpressionBuilder(exp.replaceOperators()).build()
            val result = calc.evaluate().toString()
            display.result.set(result)
        } catch (e: ArithmeticException) {
            display.result.set(e.message)
        } catch (e: IllegalArgumentException) {
            display.result.set("ERROR")
        } catch (e: Exception) {
            display.result.set("ERROR")
        }
    }

}

@BindingAdapter(value = ["bindCalc", "bindCalcAttrChanged"], requireAll = false)
fun setCalc(view: EditText, observable: ObservableField<String>, listener: InverseBindingListener) {
    val str = observable.get() ?: ""
    val digit = if (str.isNotEmpty()) str else " $str"
    val displayCalc = view.text?.toString() ?: ""
    if (displayCalc != digit) view.text.insert(view.selectionStart, digit)

    view.addTextChangedListener(TextListener(listener))
}

@InverseBindingAdapter(attribute = "bindCalc", event = "bindCalcAttrChanged")
fun getCalc(view: EditText): String {
    return view.text.toString()
}


//@BindingAdapter("bindResult")
//fun setResult(view: TextView, observable: ObservableField<String>) {
//    view.text = observable.get() ?: ""
//
////    view.addTextChangedListener {
////        observable.set(it.toString())
////        listener.onChange()
////    }
//}


//@BindingAdapter("bindResultAttrChanged")
//fun setResultListener(view: TextView, listener: InverseBindingListener) {
////    listener?.let {
////        view.addTextChangedListener {
////            Log.i("TEST", "BINDRESULT")
////            listener.onChange()
////        }
////    }
//}


//@InverseBindingAdapter(attribute = "bindResult", event = "bindResultAttrChanged")
//fun getResult(view: TextView): String {
//    return view.text.toString()
//}
