package com.fiqi.galleryapp.data.params

data class SuperParams<T>(
  var data: T? = null,
  var onSucceeded: (data: ArrayList<T>?) -> Unit = {},
  var onFailed: (message: String) -> Unit = {},
)
