package com.example.mycalculator.Data.GroupScreenData

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contact group")
data class ContactGroup(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,

)