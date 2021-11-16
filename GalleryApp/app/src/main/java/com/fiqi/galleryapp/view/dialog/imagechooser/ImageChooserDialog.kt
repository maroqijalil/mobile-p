package com.fiqi.galleryapp.view.dialog.imagechooser

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.fiqi.galleryapp.databinding.DialogImageChooserBinding
import com.fiqi.galleryapp.view.dialog.imagechooser.ImageChooserDialogParam

class ImageChooserDialog(private val param: ImageChooserDialogParam) : DialogFragment() {

  private var binding: DialogImageChooserBinding? = null

  private var dialog: AlertDialog.Builder? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)

    dialog = AlertDialog.Builder(context)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    binding = DialogImageChooserBinding.inflate(layoutInflater)

    binding?.imageChooserBtnGallery?.setOnClickListener {
      param.onPickImageClick()
      dismiss()
    }

    binding?.imageChooserBtnCamera?.setOnClickListener {
      param.onTakeImageClick()
      dismiss()
    }

    dialog?.apply {
      setTitle("Ambil Gambar")
      setView(binding?.root)
      setPositiveButton("") { _, _ -> }
      setNegativeButton("") { _, _ -> }
    }
    return dialog?.create()!!
  }

  override fun onDestroy() {
    super.onDestroy()

    if (binding != null) {
      binding = null
    }
    if (dialog != null) {
      dialog = null
    }
  }
}
