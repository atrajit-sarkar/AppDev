package com.example.mychattingapp.LocaldbLogics.DAO.daoMethods

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Query("SELECT * FROM messages")
    fun getAllMessages(): Flow<List<Message>>

    @Delete
    suspend fun deleteMessages(message: Message)

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateMessage(message: Message)
}