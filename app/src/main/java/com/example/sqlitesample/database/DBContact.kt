package com.example.sqlitesample.database

import android.provider.BaseColumns

object DBContact
{
    object ContactEntry : BaseColumns
    {
        const val TABLE_NAME = "contact"
        const val COLUMN_NAME_USER = "name"
        const val COLUMN_NAME_PHONE = "phone"
        const val COLUMN_NAME_ADDRESS = "address"
    }
}