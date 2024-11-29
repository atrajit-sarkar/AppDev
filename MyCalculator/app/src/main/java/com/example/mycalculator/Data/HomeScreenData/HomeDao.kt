package com.example.mycalculator.Data.HomeScreenData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: DummyContacts)

    @Query("SELECT * FROM DummyContacts")
    fun getAllContacts(): Flow<List<DummyContacts>>

    @Query("DELETE FROM DummyContacts WHERE id = :id")
    suspend fun deleteContact(id: Int)

    // Edit a contact
    @Query("UPDATE DummyContacts SET name = :name, phoneNumber = :phoneNumber, email = :email, dob = :dob, groupId = :groupId WHERE id = :id")
    suspend fun editContact(id: Int, name: String, phoneNumber: String, email: String, dob: String, groupId: Int)

    @Query("SELECT * FROM DummyContacts WHERE groupId = :groupId")
    fun getGroupContacts(groupId: Int): Flow<List<DummyContacts>>
}
