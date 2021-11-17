package com.fiqi.galleryapp.view.dialog.confirmation

data class ConfirmationDialogParam(
  var title: String = "Konfirmasi",
  var message: String = "",
  var positiveText: String = "Ya",
  var negativeText: String = "Batal",
  var onPositiveClick: () -> Unit = {},
  var onNegativeClick: () -> Unit = {}
)
