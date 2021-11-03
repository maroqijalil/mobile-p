package com.example.contactapp.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.adapter.ContactListAdapter
import com.example.contactapp.databinding.FragmentMainBinding
import com.example.contactapp.dialog.AddContactDialog
import com.example.contactapp.model.ContactModel
import com.example.transactionapp.db.sqlite.ContactDatabase
import java.util.*

class MainFragment : Fragment() {

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

    binding?.mainTilSearch?.editText?.setOnEditorActionListener { view, id, _ ->
      return@setOnEditorActionListener if (id == EditorInfo.IME_ACTION_SEARCH) {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
          .hideSoftInputFromWindow(view.windowToken, 0)

        val query = binding?.mainTilSearch?.editText?.text
        if (query != null) {
          val newList = arrayListOf<ContactModel>()
          adapter.getRawList().forEach { model ->
            if (model.nama.lowercase(Locale.getDefault())
                .contains(query) || model.telepon.lowercase(Locale.getDefault()).contains(query)
            ) {
              newList.add(model)
            }
          }
          adapter.changeList(newList)
        }
        true
      } else {
        false
      }
    }

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
      adapter.changeAllList(contacts)
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
  }
}
