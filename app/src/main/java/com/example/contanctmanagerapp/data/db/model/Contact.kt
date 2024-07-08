package com.example.contanctmanagerapp.data.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "contacts_table")
@Parcelize
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val groupType: String,
    val addressId: Int, // Foreign key to Address
    val name: String,
    val phone: String,
    val email: String,
    val profilePictureColor: Int,
    var isFavorite: Boolean,
    var isBlocked: Boolean,
    val birthdate: String // Formatted date string
) : Parcelable