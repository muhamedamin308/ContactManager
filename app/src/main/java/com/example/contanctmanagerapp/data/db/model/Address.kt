package com.example.contanctmanagerapp.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class Address(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String
)
