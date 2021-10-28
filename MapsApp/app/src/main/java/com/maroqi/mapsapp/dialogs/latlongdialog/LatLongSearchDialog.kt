package com.maroqi.mapsapp.dialogs.latlongdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.maroqi.mapsapp.databinding.DialogMainSearchBinding
import com.maroqi.mapsapp.databinding.DialogSearchLatlongBinding

class LatLongSearchDialog(private val onClick: (Double, Double, Float) -> Unit) : DialogFragment() {

  var binding: DialogSearchLatlongBinding? = null
  var alertDialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DialogSearchLatlongBinding.inflate(layoutInflater)

    alertDialog?.apply {
      setTitle("Cari Lat Long")
      setView(binding?.root)
      setPositiveButton("Temukan") { dialog, _ ->
        var isValid = true

        if (binding?.mapsTiLatitude?.editText?.text.isNullOrEmpty()) {
          isValid = false
        }
        if (binding?.mapsTiLongitude?.editText?.text.isNullOrEmpty()) {
          isValid = false
        }

        if (isValid) {
          if (binding?.mapsTiZoom?.editText?.text.isNullOrEmpty()) {
            onClick(
              binding?.mapsTiLatitude?.editText?.text.toString().toDouble(),
              binding?.mapsTiLongitude?.editText?.text.toString().toDouble(),
              8f
            )
          } else {
            onClick(
              binding?.mapsTiLatitude?.editText?.text.toString().toDouble(),
              binding?.mapsTiLongitude?.editText?.text.toString().toDouble(),
              binding?.mapsTiZoom?.editText?.text.toString().toFloat()
            )
          }
        } else {
          Toast.makeText(context, "Isi isian dengan benar", Toast.LENGTH_LONG).show()
        }

        dialog.dismiss()
      }
      setNegativeButton("Batal") { dialog, _ ->
        dialog.dismiss()
      }
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
