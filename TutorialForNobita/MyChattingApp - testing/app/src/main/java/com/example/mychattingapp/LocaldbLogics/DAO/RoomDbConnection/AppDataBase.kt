package com.example.mychattingapp.LocaldbLogics.DAO.RoomDbConnection

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.UserDocId
import com.example.mychattingapp.LocaldbLogics.DAO.daoMethods.MessageDao
import com.example.mychattingapp.LocaldbLogics.DAO.daoMethods.UserDao
import com.example.mychattingapp.LocaldbLogics.DAO.daoMethods.UserDocIdDao

@Database(entities = [Message::class,User::class,UserDocId::class], version = 10, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
    abstract fun userDocIdDao(): UserDocIdDao
}