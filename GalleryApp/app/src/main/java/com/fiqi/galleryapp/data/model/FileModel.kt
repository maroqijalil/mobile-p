package com.fiqi.galleryapp.data.model

data class FileModel<T>(
  var name: String = "",
  var format: String = "",
  var file: T? = null,
  var link: String = ""
) {
  fun toMap(): Map<String, Any> {
    return mapOf(
      "name" to name,
      "format" to format,
      "file" to file!!,
      "link" to link
    )
  }
}