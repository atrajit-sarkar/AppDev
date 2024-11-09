package com.example.mychattingapp.NavHost

import android.os.Handler
import android.os.Looper

private val debounceTime = 300L
private val delayTime = 200L // Delay navigation by 500 milliseconds
private var lastClickTime = 0L

private val handler = Handler(Looper.getMainLooper())

fun navigateIfNotFast(navigation: () -> Unit = {}) {
    val currentTime = System.currentTimeMillis()
    if (currentTime - lastClickTime > debounceTime) {
        lastClickTime = currentTime
        handler.postDelayed({
            navigation()
        }, delayTime)
    }
}
