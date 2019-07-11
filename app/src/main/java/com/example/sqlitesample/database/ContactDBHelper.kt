package com.example.sqlitesample.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.sqlitesample.model.Contact

class ContactDBHelper(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {
    companion object
    {
        const val DATABASE_NAME = "contact.db"
        const val DATABASE_VERSION = 1

        const val SQL_CREATE_ENTRIES = "create table ${DBContact.ContactEntry.TABLE_NAME} (" +
                " ${BaseColumns._ID} INTEGER PRIMARY KEY ," +
                " ${DBContact.ContactEntry.COLUMN_NAME_USER} TEXT ," +
                " ${DBContact.ContactEntry.COLUMN_NAME_PHONE} TEXT," +
                " ${DBContact.ContactEntry.COLUMN_NAME_ADDRESS} TEXT)"
        const val SQL_DELETE_ENTRIES = "delete from ${DBContact.ContactEntry.TABLE_NAME}"

        private var instance : ContactDBHelper? = null
        fun newInstance(context : Context): ContactDBHelper
        {
            if (instance == null)
            {
                instance =
                    ContactDBHelper(context)
            }
            return instance as ContactDBHelper
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(p0)
    }
    fun insert(contact : Contact)
    {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(DBContact.ContactEntry.COLUMN_NAME_USER,contact.name)
            put(DBContact.ContactEntry.COLUMN_NAME_PHONE,contact.phone)
            put(DBContact.ContactEntry.COLUMN_NAME_ADDRESS,contact.address)
        }
        val newRowID = db.insert(DBContact.ContactEntry.TABLE_NAME,null,values)

    }
    fun select():MutableList<Contact>
    {
        val db = this.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            DBContact.ContactEntry.COLUMN_NAME_USER,
            DBContact.ContactEntry.COLUMN_NAME_PHONE,
            DBContact.ContactEntry.COLUMN_NAME_ADDRESS
        )
        val sortListEntries = "${DBContact.ContactEntry.COLUMN_NAME_USER} ASC"

        var cursor = db.query(
            DBContact.ContactEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortListEntries
        )
        var contactList = mutableListOf<Contact>()
        with(cursor)
        {
            while (moveToNext())
            {
                var id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                var userName = getString(getColumnIndexOrThrow(DBContact.ContactEntry.COLUMN_NAME_USER))
                var phone = getString(getColumnIndexOrThrow(DBContact.ContactEntry.COLUMN_NAME_PHONE))
                var address = getString(getColumnIndexOrThrow(DBContact.ContactEntry.COLUMN_NAME_ADDRESS))
                contactList.add(Contact(id, userName, phone, address))
            }
        }
        return  contactList
    }

    fun delete(contact : Contact)
    {
        var db = this.writableDatabase
        var selections = "${BaseColumns._ID } = ?"
        var selectionArgs = arrayOf(contact.id.toString())
        var deleteRow = db.delete(
            DBContact.ContactEntry.TABLE_NAME,
            selections,
            selectionArgs)
    }
    fun update(contact : Contact)
    {
        var db = this.writableDatabase
        var values = ContentValues().apply {
            put(DBContact.ContactEntry.COLUMN_NAME_USER,contact.name)
            put(DBContact.ContactEntry.COLUMN_NAME_PHONE,contact.phone)
            put(DBContact.ContactEntry.COLUMN_NAME_ADDRESS,contact.address)
        }
        var selection = "${BaseColumns._ID} = ?"
        var selectionArgs = arrayOf(contact.id.toString())
        var countUpdateRows = db.update(
            DBContact.ContactEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs)
    }


}