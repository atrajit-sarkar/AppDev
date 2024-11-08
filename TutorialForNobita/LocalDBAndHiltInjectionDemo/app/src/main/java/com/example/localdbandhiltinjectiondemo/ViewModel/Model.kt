package com.example.localdbandhiltinjectiondemo.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.localdbandhiltinjectiondemo.dao.User
import com.example.localdbandhiltinjectiondemo.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    val allUsers = repository.getAllUsers().asLiveData()

    fun addUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }
}
