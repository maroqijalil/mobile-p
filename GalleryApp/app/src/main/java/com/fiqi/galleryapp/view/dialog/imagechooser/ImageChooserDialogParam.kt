package com.fiqi.galleryapp.view.dialog.imagechooser

data class ImageChooserDialogParam(
  var onPickImageClick: () -> Unit = {},
  var onTakeImageClick: () -> Unit = {}
)
