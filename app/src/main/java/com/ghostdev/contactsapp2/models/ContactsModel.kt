package com.jibrin.contactsapp2.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class ContactsModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val firstName: String,

    val lastName: String,

    val phoneNumber: String,

    val email: String
)