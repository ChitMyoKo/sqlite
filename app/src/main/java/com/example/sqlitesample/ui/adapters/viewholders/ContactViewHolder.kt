package com.example.sqlitesample.ui.adapters.viewholders

import android.R
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitesample.model.Contact
import kotlinx.android.synthetic.main.contact.view.*

class ContactViewHolder(var view : View,
                        private val onClick: (contact : Contact) -> Unit,
                        private val onLongClick : (contact : Contact) -> Unit) :RecyclerView.ViewHolder(view) {
    fun setData(contact : Contact)
    {
        view.apply {
            tvName.text = contact.name
            tvPhone.text = contact.phone
            tvAddress.text = contact.address
        }
        view.setOnClickListener{
            onClick(contact)
        }
        view.setOnLongClickListener {
            onLongClick(contact)
            true
        }
    }
}
