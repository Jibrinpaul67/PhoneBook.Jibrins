package com.jibrin.contactsapp2.viewmodels.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jibrin.contactsapp2.repo.ContactsRepo
import com.jibrin.contactsapp2.viewmodels.MainActivityViewModel

class MainActivityViewModelFactory(private val contactsRepo: ContactsRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(contactsRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}