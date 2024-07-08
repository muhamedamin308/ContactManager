package com.example.contanctmanagerapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contanctmanagerapp.data.db.model.Contact
import com.example.contanctmanagerapp.data.db.model.GroupType
import com.example.contanctmanagerapp.data.repository.ContactsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactsRepo
) : ViewModel() {
    private val _favoriteStateUpdated = MutableLiveData<Contact>()
    val favoriteStateUpdated: LiveData<Contact> get() = _favoriteStateUpdated

    private val _blockStateUpdated = MutableLiveData<Contact>()
    val blockStateUpdated: LiveData<Contact> get() = _blockStateUpdated

    val allContacts: LiveData<List<Contact>> = repository.allContacts
    val allFavouriteContacts: LiveData<List<Contact>> = repository.getFavoriteContacts()
    val allBlockedContacts: LiveData<List<Contact>> = repository.getBlockedContacts()


    fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addContact(contact)
        }
    }

    fun updateContact(contact: Contact) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateContact(contact)
        }

    fun deleteContact(contact: Contact) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteContact(contact)
        }

    val getContactsWithGroup: (GroupType) -> LiveData<List<Contact>> = { type ->
        repository.getContactsByGroup.invoke(type)
    }

    val getContactWithPhoneNumber: (Contact) -> LiveData<Contact> = { contact ->
        repository.getContactByPhone.invoke(contact)
    }

    fun toggleFavoriteContact(contact: Contact) {
        viewModelScope.launch {
            repository.toggleFavoriteContact(contact.id)
            contact.isFavorite = !contact.isFavorite // Update local object state
            _favoriteStateUpdated.postValue(contact) // Notify observers
        }
    }

    fun toggleBlockContact(contact: Contact) {
        viewModelScope.launch {
            repository.toggleBlockContact(contact.id)
            contact.isBlocked = !contact.isBlocked // Update local object state
            _blockStateUpdated.postValue(contact) // Notify observers
        }
    }

    fun getContactById(contactId: Int): LiveData<Contact> {
        return repository.getContactById(contactId)
    }

}