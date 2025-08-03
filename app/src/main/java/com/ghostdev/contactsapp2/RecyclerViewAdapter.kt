package com.jibrin.phonebook

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jibrin.phonebook.databinding.CardItemBinding
import com.jibrin.phonebook.models.ContactsModel


class RecyclerViewAdapter(private val contactsList: List<ContactsModel>,
                          private val clickListener: (ContactsModel) -> Unit,
                          private val deleteListener: (ContactsModel) -> Unit): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.card_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactsList[position], clickListener, deleteListener)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }
}

class MyViewHolder(private val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(contact: ContactsModel, clickListener: (ContactsModel) -> Unit, deleteListener: (ContactsModel) -> Unit) {
        binding.nameText.text = "${contact.firstName} ${contact.lastName}"
        binding.numberText.text = contact.phoneNumber
        binding.cardItem.setOnClickListener {
            clickListener(contact)
        }
        binding.deleteImg.setOnClickListener {
            deleteListener(contact)
        }
    }
}