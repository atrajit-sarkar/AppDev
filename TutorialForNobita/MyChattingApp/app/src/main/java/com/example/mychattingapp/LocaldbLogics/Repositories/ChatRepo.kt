package com.example.mychattingapp.LocaldbLogics.Repositories

import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.daoMethods.MessageDao
import javax.inject.Inject

class ChatRepository @Inject constructor(private val messageDao: MessageDao) {
    fun getAllMessages() = messageDao.getAllMessages()
    suspend fun insertMessage(message: Message) = messageDao.insertMessage(message)
    suspend fun deleteMessage(message: Message)=messageDao.deleteMessages(message)
    suspend fun deleteAllMessage()=messageDao.deleteAllMessages()
}