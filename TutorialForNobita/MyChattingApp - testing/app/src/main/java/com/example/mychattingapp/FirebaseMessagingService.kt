package com.example.mychattingapp.LocaldbLogics.ViewModel

import android.annotation.SuppressLint
import com.example.mychattingapp.notification.sendChatNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.notification?.let {
            sendChatNotification(
                context = this,
                message = it.body ?: "New message",
                title = it.title ?: "Chat App"
            )
        }
    }
}