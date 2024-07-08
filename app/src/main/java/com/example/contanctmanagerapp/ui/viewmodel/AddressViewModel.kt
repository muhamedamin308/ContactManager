package com.example.contanctmanagerapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contanctmanagerapp.data.db.model.Address
import com.example.contanctmanagerapp.data.db.model.Contact
import com.example.contanctmanagerapp.data.repository.AddressesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repository: AddressesRepo
) : ViewModel() {

    private val _addressId = MutableLiveData<Long>()
    val addressId: LiveData<Long> get() = _addressId

    fun addAddress(address: Address) {
        viewModelScope.launch {
            val id = repository.addAddress(address)
            _addressId.postValue(id)
        }
    }

    fun updateAddress(address: Address) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAddress(address)
        }

    fun deleteAddress(address: Address) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAddress(address)
        }

    fun getAddressById(contact: Contact): LiveData<Address> =
        repository.getAddressById(contact.addressId)

    suspend fun getAddressId(address: Address): Int? =
        repository.getAddressId(address)
}
