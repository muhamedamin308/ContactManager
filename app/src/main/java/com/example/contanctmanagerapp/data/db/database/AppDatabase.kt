package com.example.contanctmanagerapp.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contanctmanagerapp.data.db.dao.AddressDao
import com.example.contanctmanagerapp.data.db.dao.ContactDao
import com.example.contanctmanagerapp.data.db.model.Address
import com.example.contanctmanagerapp.data.db.model.Contact


@Database(
    entities = [Contact::class, Address::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun addressDao(): AddressDao
}