package com.example.mychattingapp.NavHost

import android.os.Handler
import android.os.Looper

private const val debounceTime = 200L
private const val delayTime = 130L
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
