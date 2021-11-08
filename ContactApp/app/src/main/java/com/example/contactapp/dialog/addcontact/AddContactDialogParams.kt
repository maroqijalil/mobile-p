package com.example.contactapp.dialog.addcontact

import com.example.contactapp.model.ContactModel

data class AddContactDialogParams(
  var data: ContactModel? = null,
  var onPositiveClick: (model: ContactModel?) -> Unit = {}
)
