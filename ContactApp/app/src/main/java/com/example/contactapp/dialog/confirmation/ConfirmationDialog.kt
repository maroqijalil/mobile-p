package com.example.contactapp.dialog.confirmation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.contactapp.model.ContactModel

class ConfirmationDialog(
  private val params: ConfirmationDialogParams
) : DialogFragment() {

  var alertDialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    alertDialog?.apply {
      setTitle(params.title)
      setMessage(params.message)
      setPositiveButton(params.positiveText) { dialog, _ ->
        params.onPositiveClick()
        dialog.dismiss()
      }
      setNegativeButton(params.negativeText) { dialog, _ ->
        params.onNegativeClick()
        dialog.dismiss()
      }
    }

    return alertDialog?.create()!!
  }

  override fun onDetach() {
    super.onDetach()

    if (alertDialog != null) {
      alertDialog = null
    }
  }
}
