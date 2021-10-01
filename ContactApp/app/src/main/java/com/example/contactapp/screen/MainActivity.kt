package com.example.contactapp.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.adapter.ContactListAdapter
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.dialog.AddContactDialog

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private lateinit var adapter: ContactListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setupButtons()
    setupList()
  }

  private fun setupButtons() {
    binding.mainFabAdd.setOnClickListener {
      AddContactDialog { data ->
        adapter.addItem(data)
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
  }
}
