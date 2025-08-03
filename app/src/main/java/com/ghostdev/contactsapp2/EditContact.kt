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
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jibrin.contactsapp2.database.ContactDB
import com.jibrin.contactsapp2.databinding.ActivityEditContactBinding
import com.jibrin.contactsapp2.models.ContactsModel
import com.jibrin.contactsapp2.repo.ContactsRepo
import com.jibrin.contactsapp2.viewmodels.EditContactViewModel
import com.jibrin.contactsapp2.viewmodels.viewmodelfactory.EditContactViewModelFactory

class EditContact : AppCompatActivity() {
    private lateinit var binding: ActivityEditContactBinding
    private lateinit var editContactViewModel: EditContactViewModel
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var firstNameEdt: EditText
    private lateinit var lastNameEdt: EditText
    private lateinit var phoneNumberEdt: EditText
    private lateinit var emailEdt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_contact)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dao = ContactDB.getInstance(this)
        val repository = ContactsRepo(dao)
        val factory = EditContactViewModelFactory(repository)
        editContactViewModel = ViewModelProvider(this, factory)[EditContactViewModel::class.java]

        firstNameEdt = binding.firstNameEdt
        lastNameEdt = binding.lastNameEdt
        phoneNumberEdt = binding.phoneNumberEdt
        emailEdt = binding.emailEdt
        cancelButton = binding.cancelButton
        saveButton = binding.saveButton

        val selectedItemId = intent.getIntExtra("id", 0)
        editContactViewModel.getContact(selectedItemId)

        // Observe the contact LiveData
        editContactViewModel.contact.observe(this, Observer { contact ->
            contact?.let {
                firstNameEdt.setText(it.firstName)
                lastNameEdt.setText(it.lastName)
                phoneNumberEdt.setText(it.phoneNumber)
                emailEdt.setText(it.email)


                saveButton.setOnClickListener {
                    if (validateFields(firstNameEdt.text.toString(), lastNameEdt.text.toString(), phoneNumberEdt.text.toString(), emailEdt.text.toString())) {
                        editContactViewModel.updateContact(
                            ContactsModel(
                                selectedItemId,
                                firstNameEdt.text.toString(),
                                lastNameEdt.text.toString(),
                                phoneNumberEdt.text.toString(),
                                emailEdt.text.toString()
                            )
                        )
                        val intent = Intent(this, MainActivity::class.java)
                        Toast.makeText(applicationContext, "Contact successfully updated", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateFields(firstName: String, lastName: String, phoneNumber: String, email: String): Boolean {
        return !(firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank() || !email.contains("@"))
    }

}