package com.example.mycalculator.Data.GroupScreenData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: ContactGroup)

    @Query("SELECT * FROM `contact group`")
    fun getAllGroups(): Flow<List<ContactGroup>>

    @Query("DELETE FROM `contact group` WHERE id = :id")
    suspend fun deleteGroup(id: Int)

    @Query("UPDATE `contact group` SET name = :name WHERE id = :id")
    suspend fun editGroup(id: Int, name: String)

    // Get the id of last inserted group


}