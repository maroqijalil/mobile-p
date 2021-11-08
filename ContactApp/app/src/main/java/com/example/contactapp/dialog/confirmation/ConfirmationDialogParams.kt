package com.example.contactapp.dialog.confirmation

data class ConfirmationDialogParams(
  var title: String = "Konfirmasi",
  var message: String = "",
  var positiveText: String = "Ya",
  var negativeText: String = "Batal",
  var onPositiveClick: () -> Unit = {},
  var onNegativeClick: () -> Unit = {}
)
