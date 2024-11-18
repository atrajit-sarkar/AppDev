package com.example.mychattingapp.LocaldbLogics.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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


    val allChats = chatrepository.getAllMessages().asLiveData()
    val allUsers = userRepository.getAllUsers().asLiveData()

    private val _selectedMessage = MutableStateFlow<Message?>(null)
    val selectedMessage: StateFlow<Message?> = _selectedMessage

    fun selectMessage(message: Message) {
        _selectedMessage.value = message
    }

    fun clearSelectedMessage() {
        _selectedMessage.value = null
    }


    // Chatrepo Functions.........
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