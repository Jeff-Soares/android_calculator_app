package com.jx.calculator.util

import android.view.animation.Animation
import androidx.databinding.ObservableField
import com.jx.calculator.presentation.model.CalcDisplay.Mode

fun String.replaceOperators() =
    this.replace("÷", "/")
        .replace("×", "*")
        .replace("−", "-")
        .replace(",", ".")

fun String.replaceSeparator() =
    this.replace(".", ",")

fun String.removeTrailingZero() =
    if (this.substring(this.length - 2, this.length) != ".0") this
    else this.substring(0, this.length - 2)

fun ObservableField<Pair<String, Mode>>.set(str: String, mode: Mode) =
    this.set(Pair(str, mode))

fun Animation.doOnAnimationEnd(f: () -> Unit) {
    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            f.invoke()
        }
    })
}

