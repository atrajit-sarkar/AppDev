package com.example.mychattingapp.LocaldbLogics.ViewModel

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.mychattingapp.ChatsData.ChannelsData
import com.example.mychattingapp.ChatsData.UsersStatusData
import com.example.mychattingapp.FireBaseLogics.FireBseSetings.FirestoreHelper
import com.example.mychattingapp.FireBaseLogics.addUserToFirestore
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.UserDocId
import com.example.mychattingapp.LocaldbLogics.Repositories.ChatRepository
import com.example.mychattingapp.LocaldbLogics.Repositories.UserDocIdRepo
import com.example.mychattingapp.LocaldbLogics.Repositories.UserRepository
import com.example.mychattingapp.MainActivity
import com.example.mychattingapp.Utils.DateUtils.convertToUserTimeZone
import com.example.mychattingapp.notification.sendChatNotification
import com.google.ai.client.generativeai.type.content
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
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ChatAppViewModel @Inject constructor(
    private val chatrepository: ChatRepository,
    private val userRepository: UserRepository,
    private val userDocIdRepo: UserDocIdRepo,
    @ApplicationContext private val context: Context
) : ViewModel() {


    // Observe userDocIdRepo to get updated data whenever the local database changes
    val userDocIdsLiveData = userDocIdRepo.getAllUserDocIds().asLiveData()

    // Function to add userDocId to the database
    fun addUserDocId(userDocId: UserDocId) {
        viewModelScope.launch(Dispatchers.IO) {
            userDocIdRepo.insertUserDocId(userDocId)
        }
    }

    // Function to remove userDocId from the database
    fun deleteUserDocId(userdocid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userDocIdRepo.deleteUserDocId(userdocid)
        }
    }

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
    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList = _userList as StateFlow<List<User>> // Expose it as immutable

    // Create a variable to hold a string that can be called in any composable
    private val _currentUser = MutableStateFlow<String>(
        FirebaseAuth.getInstance().currentUser?.email.toString().split("@")[0]
    )
    val currentUser = _currentUser.asStateFlow()


    // Firebase Auth instance
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // MutableStateFlow for the current user ID
    private val _currentUserId = MutableStateFlow<String?>(null) // Allow null values initially
    val currentUserId = _currentUserId.asStateFlow()

    // LiveData for observing user doc ID list
    private val _userDocIdList = MutableLiveData<List<String>>()
    val userDocIdListGlobal: LiveData<List<String>> get() = _userDocIdList

    init {
        // Listen to Firebase Auth state changes
        firebaseAuth.addAuthStateListener { auth ->
            val newUserId = auth.currentUser?.uid
            _currentUserId.value = newUserId
            updateUserDocIdList(newUserId)
        }
    }

    // Function to update the user document ID list
    private fun updateUserDocIdList(newUserId: String?) {
        // Ensure the list contains a non-null value or handle empty state
        _userDocIdList.value = if (newUserId != null) {
            listOf(newUserId)
        } else {
            emptyList()
        }
    }


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

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onSignUpSuccess: () -> Unit,
        onSignUpFail: () -> Unit,
        onVerificationSent: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                user.sendEmailVerification()
                                    .addOnCompleteListener { verificationTask ->
                                        if (verificationTask.isSuccessful) {
                                            onVerificationSent() // Notify verification email is sent
                                            _currentUser.value =
                                                user.email.toString()
                                                    .split("@")[0] // Update current user state

                                            // Add user to Firestore
                                            val newUser = User(
                                                uid = user.uid,
                                                userName = email.split("@")[0],
                                                password = password,
                                                messageCounter = "0",
                                                messageSentTime = "",
                                                recentMessage = "",
                                                activeStatus = ""
                                            )
                                            addUserToFirestore(newUser)
                                        } else {
                                            onSignUpFail() // Handle failure to send verification email
                                            _authError.value =
                                                verificationTask.exception?.message.toString()
                                        }
                                    }
                            }
                            onSignUpSuccess() // Call success callback
                        } else {
                            onSignUpFail() // Handle sign-up failure
                            _authError.value = task.exception?.message.toString()
                        }
                    }
            } catch (e: Exception) {
                Log.d("Error", "Error during sign-up: ${e.message}")
            }
        }
    }


    //Create a contactLoading variable
    private val _contactLoading = MutableStateFlow(false)
    val contactLoading = _contactLoading.asStateFlow()

    // Create a list of userDocId:String and a function to add a function to add a userDocId to the list
    private val _userDocId = MutableStateFlow<List<String>>(emptyList())
    val userDocId = _userDocId.asStateFlow()


//    fun addUserDocId() {
//        // Create a new list by adding the new item to the existing list
//        for (userId in userDocIds.value!!) {
//            _userDocId.value += userId.userdocid
//        }
//    }

    private var firestoreListener: ListenerRegistration? = null

    init {
        // Observe changes in userDocIdsLiveData
        userDocIdsLiveData.observeForever { userDocIds ->
            Log.d("ViewModelUserDocIds", ": $userDocIds")
            val userDocIdList = userDocIds.map { it.userdocid }
            _userDocIdList.value = _userDocIdList.value?.plus(userDocIdList)

            // Reinitialize Firestore listener with the updated list
            userDocIdListGlobal.value?.let { fetchUsersInRealTime(it) }
        }
    }

    private fun fetchUsersInRealTime(userDocIdList: List<String>) {
        // Remove the old listener
        firestoreListener?.remove()

        // Do nothing if the list is empty
        if (userDocIdList.isEmpty()) {
            _userList.value = emptyList()
            return
        }

        val db = FirestoreHelper.instance
        _contactLoading.value = true

        try {
            firestoreListener = db.collection("users")
                .whereIn("uid", userDocIdList)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        Log.e("ChatAppViewModel", "Error fetching users: ${error.message}")
                        _contactLoading.value = false
                        return@addSnapshotListener
                    }

                    if (snapshots != null) {
                        val currentUsers = _userList.value?.toMutableList() ?: mutableListOf()
                        val userMap = currentUsers.associateBy { it.uid }.toMutableMap()

                        for (docChange in snapshots.documentChanges) {
                            val doc = docChange.document
                            val user = User(
                                uid = doc.getString("uid") ?: "",
                                userName = doc.getString("userName") ?: "",
                                password = doc.getString("password") ?: "",
                                messageSentTime = doc.getString("messageSentTime") ?: "",
                                recentMessage = doc.getString("recentMessage") ?: "",
                                messageCounter = doc.getString("messageCounter") ?: "",
                                activeStatus = doc.getString("activeStatus") ?: "",
                                userDocId = doc.getString("userDocId") ?: ""
                            )

                            when (docChange.type) {
                                DocumentChange.Type.ADDED -> {
                                    userMap[user.uid] = user
                                }

                                DocumentChange.Type.MODIFIED -> {
                                    userMap[user.uid] = user
                                }

                                DocumentChange.Type.REMOVED -> {
                                    userMap.remove(user.uid)
                                }
                            }
                        }

                        _userList.value = userMap.values.sortedBy { it.userName }
                    }
                }
        } catch (e: Exception) {
            Log.e("ChatAppViewModel", "Error fetching users: ${e.message}", e)
        } finally {
            _contactLoading.value = false
        }
    }


    private val _allChats = MutableStateFlow<List<Message>>(emptyList())
    val allChats: StateFlow<List<Message>> = _allChats

    private val _chatLoading = MutableStateFlow(false)
    val chatLoading = _chatLoading.asStateFlow()
//    var lastVisible: DocumentSnapshot? = null

    // Create a variable to store messageCounter value in Int
    private val _messageCounter = MutableStateFlow(0)
    val messageCounter = _messageCounter.asStateFlow()

    init {
        // Automatically observe the LiveData and update Firestore query when the data changes
        userDocIdsLiveData.observeForever { userDocIds ->
            // Extract the userdocid values and update LiveData
            Log.d("ViewModelUserDocIds", ": $userDocIds")
            val userDocIdList = userDocIds.map { it.userdocid }
            _userDocIdList.value = _userDocIdList.value?.plus(userDocIdList)

            // Perform Firestore query with the updated list
            userDocIdListGlobal.value?.let { fetchChatsInRealTime(it) }
        }
    }

    //Create a variable to store a uid tye string
    private val _uidForNotification = MutableStateFlow<String>("")
    val uidForNotification = _uidForNotification.asStateFlow()

    // Function to change the value of the uid variable
    fun changeUid(value: String) {
        _uidForNotification.value = value
    }

    //    Fetch chats in real-time
    // Function to fetch chats in real-time and append new data in order
    private fun fetchChatsInRealTime(userDocIdList: List<String>) {
        val db = FirestoreHelper.instance
        val userId = currentUserId.value ?: return
        _chatLoading.value = true // Indicate that chats are loading

        try {
            // Query 1: Messages where the current user is the receiver
            val receiverQuery =
                db.collection("messages")
                    .whereEqualTo("receiver", userId)
                    .whereIn("sender", userDocIdList)


            // Query 2: Messages where the current user is the sender
            val senderQuery = db.collection("messages")
                .whereEqualTo("sender", userId)
                .whereIn("receiver", userDocIdList)

            val combinedChats = mutableMapOf<String, Message>()

            // Listen for receiver query
            receiverQuery?.addSnapshotListener { receiverSnapshots, error ->
                if (error != null) {
                    Log.e("ChatAppViewModel", "Error fetching messages: ${error.message}")
                    _chatLoading.value = false
                    return@addSnapshotListener
                }

                receiverSnapshots?.let {
                    for (doc in it.documentChanges) {
                        processDocumentChange(doc, combinedChats)
                    }
                    updateChatList(combinedChats)
                }
            }

            // Listen for sender query
            senderQuery.addSnapshotListener { senderSnapshots, error ->
                if (error != null) {
                    Log.e("ChatAppViewModel", "Error fetching messages: ${error.message}")
                    _chatLoading.value = false
                    return@addSnapshotListener
                }

                senderSnapshots?.let {
                    for (doc in it.documentChanges) {
                        processDocumentChange(doc, combinedChats)
                    }
                    updateChatList(combinedChats)
                }
            }
        } catch (e: Exception) {
            Log.e("ChatAppViewModel", "Error fetching messages: ${e.message}", e)
        } finally {
            _chatLoading.value = false // Indicate that chats are no longer loading
        }
    }

    // Helper function to process document changes
    private fun processDocumentChange(
        docChange: DocumentChange,
        combinedChats: MutableMap<String, Message>
    ) {
        val doc = docChange.document
        val message = Message(
            id = doc.getLong("id")?.toInt() ?: 0,
            chatId = doc.getLong("chatId")?.toInt() ?: 0,
            sender = doc.getString("sender") ?: "",
            receiver = doc.getString("receiver") ?: "",
            text = doc.getString("text") ?: "",
            timestamp = convertToUserTimeZone(doc.getString("timestamp") ?: ""),
            reaction = doc.getString("reaction") ?: "",
            icons = doc.getString("icons") ?: "",
            messageId = doc.id,
            password = doc.getString("password") ?: "",
            viewOnce = doc.getString("viewOnce") ?: "",
        )

        // Notification logic for the current user
        if (message.receiver == currentUserId.value && message.icons == "singleTick") {
            if (uidForNotification.value != "" && uidForNotification.value == message.sender) {
                filterTargetUser(message.sender)?.let {
                    sendChatNotification(
                        context = context,
                        message = message.text,
                        title = it.userName
                    )
                }
            } else if (uidForNotification.value == "") {
                filterTargetUser(message.sender)?.let {
                    sendChatNotification(
                        context = context,
                        message = message.text,
                        title = it.userName
                    )
                }
            }
        }

        // Update message status for "doubleTick"
        if (message.receiver == currentUserId.value && message.icons != "doubleTick" && message.icons != "doubleTickGreen") {
            val update = mapOf("icons" to "doubleTick")
            updateMessageItem(message.messageId, update)
        }

        // Handle Live Document Changes..............
        when (docChange.type) {
            DocumentChange.Type.ADDED -> combinedChats[message.messageId] = message
            DocumentChange.Type.MODIFIED -> combinedChats[message.messageId] = message
            DocumentChange.Type.REMOVED -> combinedChats.remove(message.messageId)
        }
    }

    // Helper function to update the chat list
    private fun updateChatList(combinedChats: MutableMap<String, Message>) {
        _allChats.value =
            combinedChats.values.sortedBy { it.timestamp } // Sort messages by timestamp
    }


    // Create variable to store a user
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    //    create a filterfunction to filter user given a uid and store it into the variable
    fun filterUser(uid: String): StateFlow<List<User>> {
        return _userList.map { users ->
            users.filter {
                it.uid == uid
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
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

    //Message Delivery Status....
    private val _messageSentStatus = MutableStateFlow<Boolean>(false)
    val messageSentStatus: StateFlow<Boolean> = _messageSentStatus
    fun changeMessageSentStatus(value: Boolean) {
        _messageSentStatus.value = value
    }

    //Update UserItem in dataBase (We will update this to directly query using documentId implementation to optimize the function).............
    fun updateUserItem(uid: String?, updateMap: Map<String, Any>?) {
        // Validate inputs
        if (uid.isNullOrEmpty()) {
            Log.e("Firestore", "Invalid uid: Cannot be null or empty")
            return
        }
        if (updateMap.isNullOrEmpty()) {
            Log.e("Firestore", "Invalid updateMap: Cannot be null or empty")
            return
        }

        // Query to find the document with matching uid field
        db.collection("users")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    Log.e("Firestore", "No user found with the specified uid")
                    return@addOnSuccessListener
                }

                // Assume there's only one matching document
                for (document in querySnapshot.documents) {
                    db.collection("users")
                        .document(document.id)
                        .update(updateMap)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Document updated successfully!")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Error updating document: ${e.message}", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error finding user: ${e.message}", e)
            }
    }

    // Create a variable isAppMinimized to store boolean type
    private val _isAppMinimized = MutableStateFlow(false)
    val isAppMinimized: StateFlow<Boolean> = _isAppMinimized

    // Create a function to change the value of isAppMinimized
    fun changeAppMinimizedState(value: Boolean) {
        _isAppMinimized.value = value
    }

    // Temporary uid
    private val _tempUid = MutableStateFlow("")
    val tempUid: StateFlow<String> = _tempUid
    fun changeTempUid(value: String) {
        _tempUid.value = value
    }


}