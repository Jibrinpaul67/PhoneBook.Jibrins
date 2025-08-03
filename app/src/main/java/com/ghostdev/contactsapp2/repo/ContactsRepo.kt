package com.jibrin.contactsapp2.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jibrin.contactsapp2.database.ContactDB
import com.jibrin.contactsapp2.models.ContactsModel

class ContactsRepo(private val contactDB: ContactDB) {

    suspend fun addContact(contact: ContactsModel) {
        contactDB.contactsDao().addContact(contact)
    }

    suspend fun editContact(contact: ContactsModel) {
        contactDB.contactsDao().editContact(contact)
    }

    suspend fun deleteContact(contact: ContactsModel) {
        contactDB.contactsDao().deleteContact(contact)
    }

    fun getAllContacts(): LiveData<List<ContactsModel>> {
       return contactDB.contactsDao().getAllContacts()
    }

    suspend fun getContact(id: Int): ContactsModel {
        return contactDB.contactsDao().getContact(id)
    }
}