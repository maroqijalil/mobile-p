package com.fiqi.galleryapp.view.dialog.confirmation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmationDialog(
  private val param: ConfirmationDialogParam
) : DialogFragment() {

  var alertDialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    alertDialog?.apply {
      setTitle(param.title)
      setMessage(param.message)
      setPositiveButton(param.positiveText) { dialog, _ ->
        param.onPositiveClick()
        dialog.dismiss()
      }
      setNegativeButton(param.negativeText) { dialog, _ ->
        param.onNegativeClick()
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
