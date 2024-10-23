package com.example.simplecalculator.MathsLogic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


fun mathsOperation(text: String): String {
    var input = text

    try {
        if (input.contains("+")) {
            input = (input.split("+")[0].toDouble() + input.split("+")[1].toDouble()).toString()
        } else if (input.contains("-")) {
            input = (input.split("-")[0].toDouble() - input.split("-")[1].toDouble()).toString()
        } else if (input.contains("×")) {
            input = (input.split("×")[0].toDouble() * input.split("×")[1].toDouble()).toString()
        } else if (input.contains("÷")) {
            val divisor = input.split("÷")[1].toDouble()
            if (divisor == 0.0) return "Error: Division by zero"
            input = (input.split("÷")[0].toDouble() / divisor).toString()
        } else if (input.contains("%")) {
            input = (input.split("%")[0].toDouble() % input.split("%")[1].toDouble()).toString()
        }
    } catch (e: NumberFormatException) {
        return "Error: Invalid number format"
    } catch (e: Exception) {
        return "Error: Something went wrong"
    }

    return input
}
