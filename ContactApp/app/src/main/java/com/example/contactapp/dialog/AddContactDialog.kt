package com.example.contactapp.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.contactapp.databinding.DialogAddContactBinding
import com.example.contactapp.model.ContactModel

class AddContactDialog(
  private val onPositiveClick: (contact: ContactModel) -> Unit
) : DialogFragment() {

  var binding: DialogAddContactBinding? = null
  var alertDialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DialogAddContactBinding.inflate(layoutInflater)

    alertDialog?.apply {
      setView(binding?.root)
      setPositiveButton("Tambahkan") { dialog, _ ->
        var isValid = true

        if (binding?.dialogAcTilName?.editText?.text.toString().isEmpty()) {
          binding?.dialogAcTilName?.editText?.error = "Isian tidak boleh kosong"
          isValid = false
        }
        if (binding?.dialogAcTilNum?.editText?.text.toString().isEmpty()) {
          binding?.dialogAcTilNum?.editText?.error = "Isian tidak boleh kosong"
          isValid = false
        }

        if (isValid) {
          onPositiveClick(
            ContactModel(
              nama = binding?.dialogAcTilName?.editText?.text.toString(),
              telepon = binding?.dialogAcTilNum?.editText?.text.toString()
            )
          )
          dialog.dismiss()
        }
      }
      setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
    }

    return alertDialog?.create()!!
  }

  override fun onDetach() {
    super.onDetach()

    if (binding != null) {
      binding = null
    }
    if (alertDialog != null) {
      alertDialog = null
    }
  }
}
