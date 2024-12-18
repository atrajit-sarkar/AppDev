package com.example.mychattingapp.LocaldbLogics.ViewModel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mychattingapp.ChatsData.ChannelsData
import com.example.mychattingapp.ChatsData.UsersStatusData
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.example.mychattingapp.LocaldbLogics.Repositories.ChatRepository
import com.example.mychattingapp.LocaldbLogics.Repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatAppViewModel @Inject constructor(
    private val chatrepository: ChatRepository, private val userRepository: UserRepository
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



    val allChats = chatrepository.getAllMessages().asLiveData()
    val allUsers = userRepository.getAllUsers().asLiveData()

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
        return _selectedMessages.value.any { it.id == message.id }
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


}