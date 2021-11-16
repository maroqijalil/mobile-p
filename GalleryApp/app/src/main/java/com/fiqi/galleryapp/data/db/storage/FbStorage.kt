package com.fiqi.galleryapp.data.db.storage

import com.google.firebase.storage.FirebaseStorage

class FbStorage {
  private val fbStorage = FirebaseStorage.getInstance()

  fun getInstance(): FirebaseStorage {
    return fbStorage
  }
}