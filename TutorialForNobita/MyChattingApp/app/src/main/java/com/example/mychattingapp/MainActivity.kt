package com.example.mychattingapp

import TestChatScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mychattingapp.ChatsData.Message
import com.example.mychattingapp.Screens.ChatScreen
//import com.example.mychattingapp.Screens.Message
import com.example.mychattingapp.ui.theme.MyChattingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sampleMessages = listOf(
                Message("Alice", "Hello! How are you?", "21:00"),
                Message("Bob", "I'm good, thanks! What about you?", "21:02"),
                Message("Alice", "All good here too!", "21:05")
            )
            ChatScreen(
                contactName = "GB Tukun2.0",
                lastSeen = "last seen today at 21:02",
            )
//            TestChatScreen()

        }
    }
}

