package com.example.mychattingapp.LocaldbLogics.DAO.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String="",
    var userName:String,
    var password:String,
    var messageSentTime:String,
    var recentMessage:String,
    var messageCounter:String,

)