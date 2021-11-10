package com.example.contactapp.dialog.addcontact

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.contactapp.databinding.DialogAddContactBinding
import com.example.contactapp.model.ContactModel

class AddContactDialog(
  private val params: AddContactDialogParams
) : DialogFragment() {

  var binding: DialogAddContactBinding? = null
  var alertDialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DialogAddContactBinding.inflate(layoutInflater)

    val positiveText = if (params.data != null) {
      binding?.dialogAcTilName?.editText?.setText(params.data?.nama)
      binding?.dialogAcTilNum?.editText?.setText(params.data?.telepon)
      "Simpan"
    } else {
      "Tambahkan"
    }

    alertDialog?.apply {
      setView(binding?.root)
      setPositiveButton(positiveText) { dialog, _ ->
        if (
          binding?.dialogAcTilName?.editText?.text.toString().isEmpty()
          || binding?.dialogAcTilNum?.editText?.text.toString().isEmpty()
        ) {
          params.onPositiveClick(null)
        } else {
          params.onPositiveClick(
            ContactModel(
              nama = binding?.dialogAcTilName?.editText?.text.toString(),
              telepon = binding?.dialogAcTilNum?.editText?.text.toString()
            )
          )
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
