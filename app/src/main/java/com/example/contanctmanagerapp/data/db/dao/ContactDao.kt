package com.example.contanctmanagerapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.contanctmanagerapp.data.db.model.Contact

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts_table ORDER BY name ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts_table WHERE phone = :phoneNumber")
    fun getContactByPhoneNumber(phoneNumber: String): LiveData<Contact>

    @Query("SELECT * FROM contacts_table WHERE isFavorite = 1")
    fun getFavoriteContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts_table WHERE isBlocked = 1")
    fun getBlockedContacts(): LiveData<List<Contact>>

    @Query("UPDATE contacts_table SET isFavorite = NOT isFavorite WHERE id = :contactId")
    suspend fun toggleFavoriteContact(contactId: Int)

    @Query("UPDATE contacts_table SET isBlocked = NOT isBlocked WHERE id = :contactId")
    suspend fun toggleBlockContact(contactId: Int)

    @Query("SELECT * FROM contacts_table WHERE name = :groupType")
    fun getContactsByGroup(groupType: String): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts_table WHERE id = :contactId")
    fun getContactById(contactId: Int): LiveData<Contact>

}