package com.example.mychattingapp.NavHost

import androidx.navigation.NavController

private val debounceTime = 300L
private var lastClickTime = 0L

fun navigateIfNotFast(navigation:()->Unit={}) {
    val currentTime = System.currentTimeMillis()
    if (currentTime - lastClickTime > debounceTime) {
        navigation()
        lastClickTime = currentTime
    }
}
