package com.example.contanctmanagerapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.contanctmanagerapp.data.db.model.Address

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(address: Address): Long

    @Update
    suspend fun update(address: Address)

    @Delete
    suspend fun delete(address: Address)

    @Query("SELECT * FROM address_table WHERE id = :addressId")
    fun getAddressById(addressId: Int): LiveData<Address>

    @Query("SELECT id FROM address_table WHERE street = :street AND city = :city AND state = :state AND zipCode = :zipCode")
    suspend fun getAddressId(street: String, city: String, state: String, zipCode: String): Int?
}