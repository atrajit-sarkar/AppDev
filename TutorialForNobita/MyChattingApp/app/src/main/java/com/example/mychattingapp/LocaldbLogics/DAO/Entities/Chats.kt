package com.example.mychattingapp.LocaldbLogics.DAO.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],  // Column in User entity
            childColumns = ["chatId"],  // Column in Message entity
            onDelete = ForeignKey.CASCADE  // Deletes messages if the chat is deleted
        )
    ],
    indices = [Index(value = ["chatId"])]  // Index for faster queries on chatId
)
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val chatId: Int,  // Foreign key linking to Chat entity
    var sender: String,
    var text: String,
    var timestamp: String,
    var reaction:String
)
