package com.example.contanctmanagerapp.data.repository

import androidx.lifecycle.LiveData
import com.example.contanctmanagerapp.data.db.dao.AddressDao
import com.example.contanctmanagerapp.data.db.model.Address
import com.example.contanctmanagerapp.data.db.model.Contact
import javax.inject.Inject

class AddressesRepo @Inject constructor(
    private val addressesDao: AddressDao
) {
    suspend fun addAddress(address: Address): Long =
        addressesDao.insert(address)

    suspend fun updateAddress(address: Address) =
        addressesDao.update(address)

    suspend fun deleteAddress(address: Address) =
        addressesDao.delete(address)

    fun getAddressById(addressId: Int): LiveData<Address> =
        addressesDao.getAddressById(addressId)

    suspend fun getAddressId(address: Address): Int? =
        addressesDao.getAddressId(address.street, address.city, address.state, address.zipCode)
}
