package com.example.mychattingapp.LocaldbLogics.ViewModel

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mychattingapp.ChatsData.ChannelsData
import com.example.mychattingapp.ChatsData.UsersStatusData
import com.example.mychattingapp.FireBaseLogics.FireBseSetings.FirestoreHelper
import com.example.mychattingapp.FireBaseLogics.addUserToFirestore
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.example.mychattingapp.LocaldbLogics.Repositories.ChatRepository
import com.example.mychattingapp.LocaldbLogics.Repositories.UserRepository
import com.example.mychattingapp.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class ChatAppViewModel @Inject constructor(
    private val chatrepository: ChatRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    // UpdateScreen sortedStatusList and sorted Channel list.......
    private val _sortedStatusList = MutableStateFlow<List<UsersStatusData>>(emptyList())
    val sortedStatusList: StateFlow<List<UsersStatusData>> = _sortedStatusList

    private val _sortedChannelList = MutableStateFlow<List<ChannelsData>>(emptyList())
    val sortedChannelList: StateFlow<List<ChannelsData>> = _sortedChannelList

    fun updateSortedStatusList(newList: List<UsersStatusData>) {
        _sortedStatusList.value = newList
    }

    fun updateSortedChannelList(newList: List<ChannelsData>) {
        _sortedChannelList.value = newList
    }

    // isUpdateScreenSearchBarActive........
    private val _isUpdateScreenSearchBarActive = MutableStateFlow(false)
    val isUpdateScreenSearchBarActive: StateFlow<Boolean> = _isUpdateScreenSearchBarActive

    fun changeUpdateScreenSearchBarActiveState(value: Boolean) {
        _isUpdateScreenSearchBarActive.value = value
    }

    //isUpdateScreenSearchBarFocused........
    private val _isUpdateScreenSearchBarFocused = MutableStateFlow(false)
    val isUpdateScreenSearchBarFocused: StateFlow<Boolean> = _isUpdateScreenSearchBarFocused

    fun changeUpdateScreenSearchBarFocusedState(value: Boolean) {
        _isUpdateScreenSearchBarFocused.value = value
    }


    //    val allChats = chatrepository.getAllMessages().asLiveData()
    // creat a variable to store all chats
    val allUsers = userRepository.getAllUsers().asLiveData()

    //Create a list of selected users and a function to add user to the list
    private val _selectedUsersHomeScreen = MutableStateFlow<List<User>>(emptyList())
    val selectedUsersHomeScreen: StateFlow<List<User>> = _selectedUsersHomeScreen
    fun selectUserForHomeScreen(user: User) {
        _selectedUsersHomeScreen.value += user
    }

    private val _homeScreenSearchBarActiveState = MutableStateFlow(false)
    val homeScreenSearchBarActiveState: StateFlow<Boolean> = _homeScreenSearchBarActiveState

    fun changeHomeScreenSearchBarActiveState(value: Boolean) {
        _homeScreenSearchBarActiveState.value = value
    }

    private val _selectedMessage = MutableStateFlow<Message?>(null)
    val selectedMessage: StateFlow<Message?> = _selectedMessage

    fun selectMessage(message: Message) {
        _selectedMessage.value = message
    }

    fun clearSelectedMessage() {
        _selectedMessage.value = null
    }


    //___________________________________________________________________________________________

    // 1. Boolean to track if message selection is initiated
    private val _messageSelectInitiated = MutableStateFlow(false)
    val messageSelectInitiated: StateFlow<Boolean> = _messageSelectInitiated

    // Boolean to initiate editing Messages...............
    private val _messageEditingInitiated = MutableStateFlow(false)
    val messageEditingInitiated: StateFlow<Boolean> = _messageEditingInitiated

    // Function to change the value of editing message initiated..........
    fun isEditingInitiated(value: Boolean) {
        _messageEditingInitiated.value = value
    }

    // 2. List to hold selected messages
    private val _selectedMessages = MutableStateFlow<List<Message>>(emptyList())
    val selectedMessages: StateFlow<List<Message>> = _selectedMessages

    // 3. Function to select (add) a message
    fun selectAMessage(message: Message) {
        _selectedMessages.value += message
    }

    // 4. Function to deselect (remove) a message
    fun deselectMessage(message: Message) {
        _selectedMessages.value -= message

    }

    // 5. Function to clear all selected messages
    fun clearSelectedMessages() {
        _selectedMessages.value = emptyList()

    }

    // 6. Function to check if a message is already selected
    fun isMessageSelected(message: Message): Boolean {
        return _selectedMessages.value.any { it.messageId == message.messageId }
    }

    // 7. Function to change the value of messageSelectInitiated......
    fun isMessageSelectInitiated(value: Boolean) {
        _messageSelectInitiated.value = value
    }

    //............................................

    // Expose LazyListState using MutableStateFlow
    private val _lazyListState = MutableStateFlow(LazyListState())
    val lazyListState: StateFlow<LazyListState> = _lazyListState

    //............................................


    // Chat repo Functions.........
    fun addMessage(message: Message) {
        viewModelScope.launch {
            chatrepository.insertMessage(message)
        }
    }

    fun deleteMessage(message: Message) {
        viewModelScope.launch {
            chatrepository.deleteMessage(message)
        }
    }

    fun updateMessage(message: Message) {
        viewModelScope.launch {
            chatrepository.updateMessage(message)
        }
    }

    fun getMessageById(chatId: Int): LiveData<List<Message>> =
        chatrepository.getMessageById(chatId).asLiveData()


    fun deleteAllMessage() {
        viewModelScope.launch {
            chatrepository.deleteAllMessage()
        }
    }


    //Userrepo functions..............
    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }

    fun getUserById(userId: Int): LiveData<List<User>> =
        userRepository.getUserById(userId).asLiveData()


    fun deleteAllUser() {
        viewModelScope.launch {
            userRepository.deleteAllUser()
        }
    }


    // FireBase Logics.................

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Create a fucntion to change isLoading value
    fun changeLoadingState(value: Boolean) {
        _isLoading.value = value
    }

    // StateFlow to hold the list of users
    private val _userList = MutableLiveData<List<User>>(emptyList())
    val userList = _userList as LiveData<List<User>> // Expose it as immutable

    // Create a variable to hold a string that can be called in any composable
    private val _currentUser = MutableStateFlow<String>(
        FirebaseAuth.getInstance().currentUser?.email.toString().split("@")[0]
    )
    val currentUser = _currentUser.asStateFlow()

    // CurrentUserId
    private val _currentUserId = MutableStateFlow<String>(
        FirebaseAuth.getInstance().currentUser?.uid.toString()
    )
    val currentUserId = _currentUserId.asStateFlow()

    //Variable to store authetication issues
    private val _authError = MutableStateFlow<String>("")
    val authError = _authError.asStateFlow()


    // Function to sign in users from Firestore
    private val auth: FirebaseAuth = Firebase.auth

    fun singInWithEmailAndPassword(
        email: String,
        password: String,
        onloginsuccess: () -> Unit,
        onloginfail: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            onloginsuccess()
                            _currentUser.value = auth.currentUser?.email.toString().split("@")[0]


                        } else {
                            onloginfail()
                            _authError.value = it.exception?.message.toString()


                        }

                    }
            } catch (e: Exception) {
                // Log or handle the error
                Log.d("", "singinwithemailandpassword: ")
            }
        }
    }

    fun creatUserWithEmailAndPassword(
        email: String,
        password: String,
        onSignUpSuccess: () -> Unit,
        onSignUpFail: () -> Unit
    ) {
        viewModelScope.launch {
            try {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            onSignUpSuccess()
                            _currentUser.value = auth.currentUser?.email.toString().split("@")[0]
                            auth.currentUser?.let { it1 ->
                                User(
                                    uid = it1.uid,
                                    userName = email.split("@")[0],
                                    password = password,
                                    messageCounter = "",
                                    messageSentTime = "",
                                    recentMessage = ""
                                )
                            }?.let { it2 ->
                                addUserToFirestore(
                                    it2
                                )
                            }
                        } else {
                            onSignUpFail()
                            _authError.value = it.exception?.message.toString()

                        }
                    }
            } catch (e: Exception) {
                // Log or handle the error
                Log.d("", "singinwithemailandpassword: ")
            }

        }
    }

    //Create a contactLoading variable
    private val _contactLoading = MutableStateFlow(false)
    val contactLoading = _contactLoading.asStateFlow()

    init {
        fetchUsersInRealTime()

    }

    //    Fetch users in real-time
    private fun fetchUsersInRealTime() {
        val db = FirestoreHelper.instance
        _contactLoading.value = true
        try {
            db.collection("users")
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        Log.e("ChatAppViewModel", "Error fetching users: ${error.message}")
                        return@addSnapshotListener
                    }

                    // Map Firestore documents to User objects
                    val users = snapshots?.documents?.map {
                        User(
                            uid = it.getString("uid") ?: "",
                            userName = it.getString("userName") ?: "",
                            password = it.getString("password") ?: "",
                            messageSentTime = it.getString("messageSentTime") ?: "",
                            recentMessage = it.getString("recentMessage") ?: "",
                            messageCounter = it.getString("messageCounter") ?: ""
                        )
                    } ?: emptyList()

                    // Update the LiveData
                    _userList.value = users
                }

        } catch (e: Exception) {
            Log.d("ChatAppViewModel", "Error fetching users: ${e.message}")
        } finally {
            _contactLoading.value = false
        }

    }

    private val _allChats = MutableStateFlow<List<Message>>(emptyList())
    val allChats: StateFlow<List<Message>> = _allChats

    private val _chatLoading = MutableStateFlow(false)
    val chatLoading = _chatLoading.asStateFlow()
//    var lastVisible: DocumentSnapshot? = null

    init {
        fetchChatsInRealTime()
    }

    //    Fetch chats in real-time
    // Function to fetch chats in real-time and append new data in order
    private fun fetchChatsInRealTime() {
        val db = FirestoreHelper.instance
        _chatLoading.value = true // Indicate that chats are loading

        try {
            db.collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING) // Order by timestamp
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        Log.e("ChatAppViewModel", "Error fetching messages: ${error.message}")
                        _chatLoading.value = false
                        return@addSnapshotListener
                    }

                    if (snapshots != null) {
                        val currentChats = _allChats.value?.toMutableList() ?: mutableListOf()
                        val chatMap = currentChats.associateBy { it.messageId }.toMutableMap() // Use a map for efficient updates

                        for (docChange in snapshots.documentChanges) {
                            val doc = docChange.document
                            val message = Message(
                                id = doc.getLong("id")?.toInt() ?: 0,
                                chatId = doc.getLong("chatId")?.toInt() ?: 0,
                                sender = doc.getString("sender") ?: "",
                                receiver = doc.getString("receiver") ?: "",
                                text = doc.getString("text") ?: "",
                                timestamp = doc.getString("timestamp") ?: "",
                                reaction = doc.getString("reaction") ?: "",
                                icons = doc.getString("icons") ?: "",
                                messageId = doc.id // Use document ID for unique messageId
                            )

                            when (docChange.type) {
                                DocumentChange.Type.ADDED -> {
                                    chatMap[message.messageId] = message // Add new message
                                }

                                DocumentChange.Type.MODIFIED -> {
                                    chatMap[message.messageId] = message // Update existing message
                                }

                                DocumentChange.Type.REMOVED -> {
                                    chatMap.remove(message.messageId) // Remove the message
                                }
                            }
                        }

                        // Update the LiveData or StateFlow with the new list of messages
                        _allChats.value = chatMap.values.sortedBy { it.timestamp } // Sort by timestamp for consistency
                    }
                }
        } catch (e: Exception) {
            Log.e("ChatAppViewModel", "Error fetching messages: ${e.message}", e)
        } finally {
            _chatLoading.value = false // Indicate that chats are no longer loading
        }
    }



    //filter from userList a user that's uid matches given a uid.... Create a function....
    fun filterTargetUser(uid: String): User? {
        val targetUser = userList.value?.find { it.uid == uid }
        return targetUser

    }

    //filter from allChats list of messages whose sender_id is either current user uid or given uid and reciever_id is current user uid or give uid and return the filtered list

    fun filterTargetMessages(uid: String): StateFlow<List<Message>> {
        return _allChats.map { messages ->
            messages.filter {
                (it.sender == uid && it.receiver == (auth.currentUser?.uid
                    ?: "")) || (it.receiver == uid && it.sender == (auth.currentUser?.uid ?: ""))
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    }


    private val db = FirestoreHelper.instance

    fun setupMessageListener(context: Context) {
        db.collection("messages")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                for (document in snapshots!!.documentChanges) {
                    if (document.type == DocumentChange.Type.ADDED) {
                        val newMessage = document.document.getString("message")
                        val sender = document.document.getString("sender")
                        newMessage?.let {
                            viewModelScope.launch(Dispatchers.Main) {
                                sendNotification(context, sender ?: "Unknown", it)
                            }
                        }
                    }
                }
            }


    }

    private fun sendNotification(context: Context, sender: String, message: String) {
        val channelId = "message_notifications"

        // Intent to open the app when the notification is tapped
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Message from $sender")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

    // Update MessageItem in dataBase
    fun updateMessageItem(messageId: String?, updateMap: Map<String, Any>?) {
        // Validate inputs
        if (messageId.isNullOrEmpty()) {
            Log.e("Firestore", "Invalid messageId: Cannot be null or empty")
            return
        }
        if (updateMap.isNullOrEmpty()) {
            Log.e("Firestore", "Invalid updateMap: Cannot be null or empty")
            return
        }

        // Attempt to update the Firestore document
        db.collection("messages")
            .document(messageId)
            .update(updateMap)
            .addOnSuccessListener {
                Log.d("Firestore", "Document updated successfully!")
            }
            .addOnFailureListener { e ->
                // Log the error and ensure it doesn't crash the app
                Log.e("Firestore", "Error updating document: ${e.message}", e)
//                Log.e("Firestore", "Error updating document: ${e.message}. Retrying...", e)
//                retryUpdate(messageId, updateMap) // Implement retry logic here
            }
    }


}