package com.jibrin.phonebook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.jibrin.phonebook.models.ContactsModel

@Database(entities = [ContactsModel::class], version = 1)
abstract class ContactDB: RoomDatabase() {
    abstract fun contactsDao(): ContactsDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDB? = null
        fun getInstance(context: Context): ContactDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = databaseBuilder(
                        context,
                        ContactDB::class.java,
                        "contacts_db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}