package com.example.sqlitesample.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitesample.R
import com.example.sqlitesample.model.Contact
import com.example.sqlitesample.ui.adapters.viewholders.ContactViewHolder

class ContactAdapter(private val onClickItem : (contact : Contact) -> Unit, private val onLongClickItem : (contact : Contact) -> Unit) : RecyclerView.Adapter<ContactViewHolder>() {

    private var contactList = mutableListOf<Contact>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.contact,parent,false)
        return ContactViewHolder(view,onClick = onClickItem,onLongClick = onLongClickItem)
    }

    override fun getItemCount(): Int {
        return  contactList.count()
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.setData(contactList[position])
    }
    fun setContactList( contactList : MutableList<Contact>)
    {
        this.contactList = contactList
        notifyDataSetChanged()
    }
}