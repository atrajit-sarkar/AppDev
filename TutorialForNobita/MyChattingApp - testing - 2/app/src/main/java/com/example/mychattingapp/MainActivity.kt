package com.example.mychattingapp


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.NavHost.Navigation
import com.example.mychattingapp.Optimization.checkAndRequestBatteryOptimization
import com.example.mychattingapp.notification.createNotificationChannel
import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

val auth: FirebaseAuth = Firebase.auth

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: ChatAppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MyChattingAppTheme {
                checkAndRequestBatteryOptimization(this)
//                val intent = Intent(this, MyFirebaseMessagingService::class.java)
//                startForegroundService(intent)
                viewModel.changeAppMinimizedState(false)



                Navigation(viewModel)
//                val context = LocalContext.current
                // Update the Online Status when App is opened.....
                val updates = mapOf(
                    "activeStatus" to "Online"
                )

                viewModel.updateUserItem(auth.currentUser?.uid, updates)

            }


        }


        // Observe lifecycle changes
        lifecycle.addObserver(AppLifecycleObserver(viewModel))


    }

}

class AppLifecycleObserver(
    val viewModel: ChatAppViewModel
) : LifecycleObserver {
    // Create currentTime in HH:mm format
 // When in BackGround..............
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        val updates = mapOf(
            "activeStatus" to "lastseen at $currentTime"
        )
        // App has moved to the background
        println("App is in the background")
        Log.d("MainActivity", "onAppBackgrounded: App in the backGround")
        // Add your custom logic here
        viewModel.updateUserItem(auth.currentUser?.uid, updates)
        viewModel.changeAppMinimizedState(true)
        viewModel.changeTempUid(viewModel.uidForNotification.value)
        viewModel.changeUid("")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        val update = mapOf(
            "activeStatus" to "Online"

        )
        // App has moved to the foreground
        println("App is in the foreground")
        Log.d("MainActivity", "onAppForegrounded: App in the foreground")
        // Add your custom logic here
        viewModel.updateUserItem(auth.currentUser?.uid, update)
        viewModel.changeAppMinimizedState(false)
        viewModel.changeUid(viewModel.tempUid.value)
        viewModel.changeTempUid("")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onAppKilled() {
        // App is being killed
        println("App is being destroyed")
        Log.d("MainActivity", "onAppKilled: App is killed")
        // Add your custom logic here
    }
}

