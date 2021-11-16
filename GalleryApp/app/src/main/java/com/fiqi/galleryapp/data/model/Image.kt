package com.fiqi.galleryapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
  var id: String = "",
  var link: String = "",
  var desc: String = ""
): Parcelable
