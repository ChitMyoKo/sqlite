package com.example.sqlitesample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitesample.R
import com.example.sqlitesample.model.Contact
import com.example.sqlitesample.model.DataModel
import com.example.sqlitesample.ui.adapters.ContactAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val contactAdapter : ContactAdapter by lazy { ContactAdapter(this::onClickItem,this::onLongClickItem) }
    private lateinit var dataModel : DataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcContact.apply {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        dataModel = DataModel.newInstance(this)
    }

    override fun onResume() {
        super.onResume()
        contactAdapter.setContactList(dataModel.selectContact())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item?.itemId == R.id.menuAdd)
        {
            val intent = DataEntryActivity.newIntent(this,false)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun onClickItem(contact: Contact)
    {
        val intent = DataEntryActivity.newIntent(this,
            isEdit = true,
            id = contact.id,
            name = contact.name,
            phone = contact.phone,
            address = contact.address)
        startActivity(intent)
    }
    private fun onLongClickItem(contact: Contact)
    {
        var alertDialog = AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure to delete Contant?")
            .setPositiveButton("OK"){_,_ ->
                dataModel.deleteContact(contact)
                contactAdapter.setContactList(contactList = dataModel.selectContact())

            }
            .setNegativeButton("Cancel"){_,_ ->

            }
            .create()
        alertDialog.show()
    }
}

