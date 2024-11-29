package com.example.mycalculator.Data.HomeScreenData.Repository

import com.example.mycalculator.Data.GroupScreenData.ContactGroup
import com.example.mycalculator.Data.GroupScreenData.GroupDao
import com.example.mycalculator.Data.HomeScreenData.ContactDao
import com.example.mycalculator.Data.HomeScreenData.DummyContacts
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val contactDao: ContactDao,
    private val groupDao: GroupDao
) {
    fun getAllContacts() = contactDao.getAllContacts()
    suspend fun insertContact(DummyContacts: DummyContacts) =
        contactDao.insertContact(DummyContacts)

    suspend fun deleteContact(id: Int) = contactDao.deleteContact(id)
    suspend fun editContact(
        name: String,
        phoneNumber: String,
        email: String,
        dob: String,
        groupId: Int,
        id: Int
    ) = contactDao.editContact(id, name, phoneNumber, email, dob, groupId)

    fun getAllGroups() = groupDao.getAllGroups()
    suspend fun insertGroup(contactGroup: ContactGroup) =
        groupDao.insertGroup(contactGroup)


    suspend fun deleteGroup(id: Int) = groupDao.deleteGroup(id)
    suspend fun editGroup(id: Int, name: String) = groupDao.editGroup(id, name)

    fun getGroupContacts(groupId: Int): Flow<List<DummyContacts>> {
        return contactDao.getGroupContacts(groupId)
    }

}
