package com.example.sqlitesample.model

import android.content.Context
import com.example.sqlitesample.database.ContactDBHelper

class DataModel(private  val context: Context) {
companion object
    {
        var instance : DataModel? = null
        fun newInstance(context: Context) : DataModel
        {
            if(instance == null)
                instance = DataModel(context)
            return instance as DataModel
        }
    }
    fun insertContact(contact : Contact)
    {
        val dbHelper = ContactDBHelper.newInstance(context)
        dbHelper.insert(contact)
    }
    fun selectContact(): MutableList<Contact>
    {
        val dbHelper = ContactDBHelper.newInstance(context)
        return dbHelper.select()
    }
    fun updateContact(contact : Contact)
    {
        val dbHelper = ContactDBHelper.newInstance(context)
        dbHelper.update(contact)
    }
    fun deleteContact(contact: Contact)
    {
        val dbHelper = ContactDBHelper.newInstance(context)
        dbHelper.delete(contact)
    }
}