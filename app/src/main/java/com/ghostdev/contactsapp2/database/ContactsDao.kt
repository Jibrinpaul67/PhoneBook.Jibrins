package com.jibrin.phonebook.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jibrin.phonebook.models.ContactsModel

@Dao
interface ContactsDao {

    @Insert
    suspend fun addContact(contact: ContactsModel)

    @Update
    suspend fun editContact(contact: ContactsModel)

    @Delete
    suspend fun deleteContact(contact: ContactsModel)

    @Query("SELECT * FROM contacts_table")
    fun getAllContacts(): LiveData<List<ContactsModel>>

    @Query("SELECT * FROM contacts_table WHERE :id = id")
    suspend fun getContact(id: Int): ContactsModel
}