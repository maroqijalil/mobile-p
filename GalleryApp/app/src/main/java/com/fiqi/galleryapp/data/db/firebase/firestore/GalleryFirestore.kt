package com.fiqi.galleryapp.data.db.firebase.firestore

import com.fiqi.galleryapp.data.model.ImageModel
import com.fiqi.galleryapp.data.params.SuperParams
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class GalleryFirestore() {

  private val galleryCollection: CollectionReference =
    FirebaseFirestore.getInstance().collection("images")

  fun read(param: SuperParams<ImageModel>) {
    galleryCollection.get()
      .addOnSuccessListener { result ->
        val datas: ArrayList<ImageModel> = arrayListOf()
        result.forEach { doc ->
          val obj = doc.toObject(ImageModel::class.java)
          obj.id = doc.id
          datas.add(obj)
        }
        param.onSucceeded(datas)
      }
      .addOnFailureListener { exc ->
        param.onFailed(exc.message.toString())
      }
  }

  fun insert(param: SuperParams<ImageModel>) {
    galleryCollection.add(param.data!!)
      .addOnSuccessListener {
        param.onSucceeded(arrayListOf(param.data!!))
      }
      .addOnFailureListener { exc ->
        param.onFailed(exc.message.toString())
      }
  }

  fun delete(param: SuperParams<String>) {
    galleryCollection.document(param.data!!).delete()
      .addOnSuccessListener { param.onSucceeded(null) }
      .addOnFailureListener { exc -> param.onFailed(exc.message.toString()) }
  }
}
