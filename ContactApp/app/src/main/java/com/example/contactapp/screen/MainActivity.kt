package com.example.contactapp.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.adapter.ContactListAdapter
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.dialog.AddContactDialog
import com.example.contactapp.model.ContactModel
import com.example.transactionapp.db.sqlite.ContactDatabase

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private lateinit var adapter: ContactListAdapter

  private lateinit var database: ContactDatabase

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    database = ContactDatabase(this, arrayListOf(ContactModel()))

    setupButtons()
    setupList()
  }

  private fun setupButtons() {
    binding.mainFabAdd.setOnClickListener {
      AddContactDialog { data ->
        if (data == null) {
          showToast("Isi data dengan benar!")
        } else {
          adapter.addItem(data)

          database.insert(data)
        }
      }.show(supportFragmentManager, null)
    }
  }

  private fun setupList() {
    adapter = ContactListAdapter()
    binding.mainRvContact.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = this@MainActivity.adapter
    }

    val contacts = database.read(ContactModel(), null)
    if (contacts.size > 0) {
      adapter.changeList(contacts)
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
  }
}
