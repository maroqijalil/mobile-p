package com.example.contactapp.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.adapter.ContactListAdapter
import com.example.contactapp.databinding.FragmentMainBinding
import com.example.contactapp.dialog.AddContactDialog
import com.example.contactapp.model.ContactModel
import com.example.transactionapp.db.sqlite.ContactDatabase

class MainFragment: Fragment() {

  private var binding: FragmentMainBinding? = null

  private lateinit var adapter: ContactListAdapter

  private lateinit var database: ContactDatabase

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentMainBinding.inflate(inflater, container, false)

    database = ContactDatabase(context, arrayListOf(ContactModel()))
    setupButtons()
    setupList()

    return binding?.root
  }

  override fun onDestroy() {
    super.onDestroy()

    if (binding != null) {
      binding = null
    }
  }

  private fun setupButtons() {
    binding?.mainFabAdd?.setOnClickListener {
      AddContactDialog { data ->
        if (data == null) {
          showToast("Isi data dengan benar!")
        } else {
          adapter.addItem(data)

          database.insert(data)
        }
      }.show(parentFragmentManager, null)
    }
  }

  private fun setupList() {
    adapter = ContactListAdapter { model ->
      findNavController().navigate(
        MainFragmentDirections.actionNavMainFragmentToNavViewContactFragment(model)
      )
    }
    binding?.mainRvContact?.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(context)
      adapter = this@MainFragment.adapter
    }

    val contacts = database.read(ContactModel(), null)
    if (contacts.size > 0) {
      adapter.changeList(contacts)
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}
