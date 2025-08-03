package com.jibrin.contactsapp2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jibrin.contactsapp2.database.ContactDB
import com.jibrin.contactsapp2.databinding.ActivityAddContactBinding
import com.jibrin.contactsapp2.models.ContactsModel
import com.jibrin.contactsapp2.repo.ContactsRepo
import com.jibrin.contactsapp2.viewmodels.AddContactViewModel
import com.jibrin.contactsapp2.viewmodels.viewmodelfactory.AddContactViewModelFactory

class AddContact : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactBinding
    private lateinit var addContactViewModel: AddContactViewModel
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var firstNameEdt: EditText
    private lateinit var lastNameEdt: EditText
    private lateinit var phoneNumberEdt: EditText
    private lateinit var emailEdt: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_contact)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dao = ContactDB.getInstance(this)
        val repository = ContactsRepo(dao)
        val factory = AddContactViewModelFactory(repository)
        addContactViewModel = ViewModelProvider(this, factory) [AddContactViewModel::class.java]

        firstNameEdt = binding.firstNameEdt
        lastNameEdt = binding.lastNameEdt
        phoneNumberEdt = binding.phoneNumberEdt
        emailEdt = binding.emailEdt
        cancelButton = binding.cancelButton
        saveButton = binding.saveButton


        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            if (validateFields(firstNameEdt.text.toString(), lastNameEdt.text.toString(), phoneNumberEdt.text.toString(), emailEdt.text.toString())) {
                addContactViewModel.addContact(
                    ContactsModel(
                        0,
                        firstNameEdt.text.toString(),
                        lastNameEdt.text.toString(),
                        phoneNumberEdt.text.toString(),
                        emailEdt.text.toString()
                    )
                )
                val intent = Intent(this, MainActivity::class.java)
                Toast.makeText(applicationContext, "Contact successfully added", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields(firstName: String, lastName: String, phoneNumber: String, email: String): Boolean {
        return !(firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank() || !email.contains("@"))
    }
}