package com.jx.calculator.util

import android.view.View
import android.view.animation.Animation
import androidx.databinding.ObservableField
import com.jx.calculator.presentation.model.CalcDisplay.Mode
import java.util.*

fun String.replaceOperators() =
    this.replace("÷", "/")
        .replace("×", "*")
        .replace("−", "-")
        .replace(",", ".")
        .replace("%", "/100*")
        .replace("³√(\\d+)\\b".toRegex(), "cbrt(\$1)")
        .replace("√(\\d+)\\b".toRegex(), "sqrt(\$1)")
        .replace("Log(\\d+)\\b".toRegex(), "log(\$1)")
        .replace("Log₂(\\d+)\\b".toRegex(), "log2(\$1)")
        .replace("Log₁₀(\\d+)\\b".toRegex(), "log10(\$1)")
        .toLowerCase(Locale.ROOT)

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

fun View.startRipple() {
    isPressed = true
    isPressed = false
}

