package com.jx.calculator.util

fun String.replaceOperators() =
    this.replace("÷", "/")
        .replace("×", "*")
        .replace("−", "-")
