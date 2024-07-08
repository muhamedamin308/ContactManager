package com.example.contanctmanagerapp.di

import android.content.Context
import androidx.room.Room
import com.example.contanctmanagerapp.data.db.dao.AddressDao
import com.example.contanctmanagerapp.data.db.dao.ContactDao
import com.example.contanctmanagerapp.data.db.database.AppDatabase
import com.example.contanctmanagerapp.data.repository.AddressesRepo
import com.example.contanctmanagerapp.data.repository.ContactsRepo
import com.example.contanctmanagerapp.ui.viewmodel.AddressViewModel
import com.example.contanctmanagerapp.ui.viewmodel.ContactViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "contact_database"
    ).build()

    @Provides
    fun provideContactDao(appDatabase: AppDatabase) =
        appDatabase.contactDao()

    @Provides
    fun provideAddressDao(appDatabase: AppDatabase) =
        appDatabase.addressDao()

    @Provides
    @Singleton
    fun provideContactsRepo(contactsDao: ContactDao): ContactsRepo =
        ContactsRepo(contactsDao)

    @Provides
    @Singleton
    fun provideAddressesRepo(addressDao: AddressDao): AddressesRepo =
        AddressesRepo(addressDao)

    @Provides
    @Singleton
    fun provideContactViewModel(contactsRepo: ContactsRepo): ContactViewModel =
        ContactViewModel(contactsRepo)

    @Provides
    @Singleton
    fun provideAddressViewModel(addressesRepo: AddressesRepo): AddressViewModel =
        AddressViewModel(addressesRepo)
}
