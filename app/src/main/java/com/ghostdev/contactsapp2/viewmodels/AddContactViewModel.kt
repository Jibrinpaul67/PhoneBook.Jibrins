package com.jibrin.phonebook.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jibrin.phonebook.models.ContactsModel
import com.jibrin.phonebook.repo.ContactsRepo
import kotlinx.coroutines.launch

class AddContactViewModel(private val contactsRepo: ContactsRepo): ViewModel() {

    fun addContact(contact: ContactsModel) {
        viewModelScope.launch {
            contactsRepo.addContact(contact)
        }
    }
}