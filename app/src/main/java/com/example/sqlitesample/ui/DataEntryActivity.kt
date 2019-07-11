package com.example.sqlitesample.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.sqlitesample.R
import com.example.sqlitesample.model.Contact
import com.example.sqlitesample.model.DataModel
import kotlinx.android.synthetic.main.activity_data_entry.*

class DataEntryActivity : AppCompatActivity() {
    private var isEdit = false
    private var contactDate : Contact? = null
    private lateinit var dataModel : DataModel
    companion object
    {
        const val IE_IS_EDIT = "isEdit"
        const val IE_ID = "id"
        const val IE_NAME = "name"
        const val IE_PHONE = "phone"
        const val IE_ADDRESS = "address"
        fun newIntent(context: Context,isEdit : Boolean,
                      id : Int? = null,
                      name: String? = null,
                      phone: String? = null,
                      address:String? = null ): Intent
        {
            var intent = Intent(context,DataEntryActivity::class.java)
            intent.putExtra(IE_IS_EDIT,isEdit)
            intent.putExtra(IE_ID,id)
            intent.putExtra(IE_NAME,name)
            intent.putExtra(IE_PHONE,phone)
            intent.putExtra(IE_ADDRESS,address)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(intent.getBooleanExtra(IE_IS_EDIT, false))
        {
            isEdit = true
            val name = intent.getStringExtra(IE_NAME)
            val id = intent.getIntExtra(IE_ID,0)
            val phone = intent.getStringExtra(IE_PHONE)
            val address = intent.getStringExtra(IE_ADDRESS)
            contactDate = Contact(id,name,phone,address)
            edName.setText(name)
            edPhone.setText(phone)
            edAddress.setText(address)
            btnContact.setText("Update Contact")
        }

        dataModel = DataModel.newInstance(this)
        btnContact.setOnClickListener {
            if(edName.text.isBlank()){
                edName.error = "Please enter name"
                return@setOnClickListener
                }
            if(edPhone.text.isBlank())
            {
                edPhone.error = "Please enter phone"
                return@setOnClickListener
            }
            if(edAddress.text.isBlank())
            {
                edAddress.error = "Please enter address"
                return@setOnClickListener
            }
            val name = edName.text.toString()
            val phone = edPhone.text.toString()
            val address = edAddress.text.toString()

            if(isEdit)
            {
                contactDate = Contact(contactDate?.id,name,phone,address)
                dataModel.updateContact(contactDate!!)
            }
            else{
                contactDate = Contact(null,name,phone,address)
                dataModel.insertContact(contactDate!!)
            }

            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)
        {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
