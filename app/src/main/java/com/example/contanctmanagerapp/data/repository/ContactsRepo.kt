package com.example.contanctmanagerapp.data.repository

import androidx.lifecycle.LiveData
import com.example.contanctmanagerapp.data.db.dao.ContactDao
import com.example.contanctmanagerapp.data.db.model.Contact
import com.example.contanctmanagerapp.data.db.model.GroupType
import javax.inject.Inject

class ContactsRepo @Inject constructor(
    private val contactsDao: ContactDao
) {
    val allContacts = contactsDao.getAllContacts()

    suspend fun addContact(contact: Contact) =
        contactsDao.insert(contact)

    suspend fun updateContact(contact: Contact) =
        contactsDao.update(contact)

    suspend fun deleteContact(contact: Contact) =
        contactsDao.delete(contact)

    val getContactByPhone: (Contact) -> LiveData<Contact> = { contact ->
        contactsDao.getContactByPhoneNumber(contact.phone)
    }

    val getFavoriteContacts: () -> LiveData<List<Contact>> = {
        contactsDao.getFavoriteContacts()
    }

    val getBlockedContacts: () -> LiveData<List<Contact>> = {
        contactsDao.getBlockedContacts()
    }

    suspend fun toggleFavoriteContact(contactId: Int) {
        contactsDao.toggleFavoriteContact(contactId)
    }

    suspend fun toggleBlockContact(contactId: Int) {
        contactsDao.toggleBlockContact(contactId)
    }

    val getContactsByGroup: (GroupType) -> LiveData<List<Contact>> = { type ->
        contactsDao.getContactsByGroup(type.name)
    }

    fun getContactById(contactId: Int): LiveData<Contact> {
        return contactsDao.getContactById(contactId)
    }
}