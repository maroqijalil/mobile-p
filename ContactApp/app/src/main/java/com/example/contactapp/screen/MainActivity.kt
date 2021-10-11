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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}
