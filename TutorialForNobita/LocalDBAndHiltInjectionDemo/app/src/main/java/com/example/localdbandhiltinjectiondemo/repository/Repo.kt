package com.example.localdbandhiltinjectiondemo.repository

import com.example.localdbandhiltinjectiondemo.dao.User
import com.example.localdbandhiltinjectiondemo.dao.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {
    fun getAllUsers() = userDao.getAllUsers()
    suspend fun insertUser(user: User) = userDao.insertUser(user)
}
