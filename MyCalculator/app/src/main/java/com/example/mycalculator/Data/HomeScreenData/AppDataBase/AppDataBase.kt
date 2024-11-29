package com.example.mycalculator.Data.HomeScreenData.AppDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mycalculator.Data.GroupScreenData.ContactGroup
import com.example.mycalculator.Data.GroupScreenData.GroupDao
import com.example.mycalculator.Data.HomeScreenData.ContactDao
import com.example.mycalculator.Data.HomeScreenData.DummyContacts

@Database(entities = [DummyContacts::class,ContactGroup::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ContactDao(): ContactDao
    abstract fun GroupDao(): GroupDao
}
