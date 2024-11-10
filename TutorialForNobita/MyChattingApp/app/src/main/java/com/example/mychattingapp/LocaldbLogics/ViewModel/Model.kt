package com.example.mychattingapp.LocaldbLogics.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.example.mychattingapp.LocaldbLogics.Repositories.ChatRepository
import com.example.mychattingapp.LocaldbLogics.Repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatAppViewModel @Inject constructor(
    private val chatrepository: ChatRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val allChats = chatrepository.getAllMessages().asLiveData()
    val allUsers = userRepository.getAllUsers().asLiveData()


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

    fun deleteAllMessage(){
        viewModelScope.launch {
            chatrepository.deleteAllMessage()
        }
    }


    //Userrepo functions..............
    fun addUser(user: User){
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }
    fun deleteUser(user: User){
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }
    fun deleteAllUser(){
        viewModelScope.launch {
            userRepository.deleteAllUser()
        }
    }

}