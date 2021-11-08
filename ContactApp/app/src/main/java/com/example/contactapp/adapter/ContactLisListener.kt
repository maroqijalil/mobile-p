package com.example.contactapp.adapter

import com.example.contactapp.model.ContactModel

interface ContactLisListener {
  fun onItemClick(model: ContactModel)
  fun onItemEditClick(model: ContactModel)
  fun onItemDeleteClick(model: ContactModel)
}
