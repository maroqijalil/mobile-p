package com.fiqi.galleryapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
  var id: String = "",
  var title: String = "",
  var link: String = "",
  var desc: String = ""
): Parcelable {
  fun toMap(): Map<String, Any> {
    return mapOf(
      "title" to title,
      "link" to link,
      "desc" to desc
    )
  }
}
