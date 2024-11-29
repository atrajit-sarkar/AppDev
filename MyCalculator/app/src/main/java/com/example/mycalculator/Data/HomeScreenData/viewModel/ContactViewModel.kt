package com.example.mycalculator.Data.HomeScreenData.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mycalculator.Data.GroupScreenData.ContactGroup
import com.example.mycalculator.Data.HomeScreenData.DummyContacts
import com.example.mycalculator.Data.HomeScreenData.Repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val repository: ContactRepository) :
    ViewModel() {
    val allContacts = repository.getAllContacts()

    fun addUser(contacts: DummyContacts) {
        viewModelScope.launch {
            repository.insertContact(contacts)
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            repository.deleteContact(id)
        }
    }

    fun updateUser(contacts: DummyContacts) {
        viewModelScope.launch {
            repository.editContact(
                name = contacts.name,
                phoneNumber = contacts.phoneNumber,
                email = contacts.email,
                dob = contacts.dob,
                groupId = contacts.groupId,
                id = contacts.id
            )
        }
    }

    private val _selectedContactList = MutableStateFlow<List<DummyContacts>>(emptyList())
    val selectedContactList: StateFlow<List<DummyContacts>> = _selectedContactList


    // Function to clear the selected contact list
    fun clearSelectedContactList() {
        _selectedContactList.value = emptyList()
    }

    // Function to add a contact to the selected contact list
    fun addSelectedContact(contact: DummyContacts) {
        _selectedContactList.value = _selectedContactList.value + contact
    }

    // Function to remove a contact from the selected contact list
    fun removeSelectedContact(contact: DummyContacts) {
        _selectedContactList.value = _selectedContactList.value - contact
    }

    val allGroups = repository.getAllGroups()
    fun addGroup(group: ContactGroup) {
        viewModelScope.launch {
            repository.insertGroup(group)
        }
    }


    fun deleteGroup(id: Int) {
        viewModelScope.launch {
            repository.deleteGroup(id)
        }
    }
    fun editGroup(id: Int, name: String) {
        viewModelScope.launch {
            repository.editGroup(id, name)
        }
    }

    fun getGroupContacts(groupId: Int): Flow<List<DummyContacts>> {
        return repository.getGroupContacts(groupId)
    }





}
