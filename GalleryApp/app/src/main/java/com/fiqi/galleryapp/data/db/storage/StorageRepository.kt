package com.fiqi.galleryapp.data.db.storage

import android.net.Uri
import com.fiqi.galleryapp.data.model.FileModel
import com.fiqi.galleryapp.data.params.SuperParams
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.Continuation

class StorageRepository {
  private val storage = FbStorage().getInstance().getReference("images_data")

  fun uploadFile(param: SuperParams<FileModel<Uri>>) {
    val storageRef: StorageReference = storage.child(FileModel<Uri>().getRefName())

    val fileName = param.data?.name + "." + param.data?.format

    val filePath = storageRef.child(fileName)
    filePath.putFile(param.data?.file!!)
      .continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
        if(!task.isSuccessful) {
          task.exception?.let { exc ->
            throw exc
          }
        }
        return@Continuation filePath.downloadUrl
      }).addOnCompleteListener { task ->
        if (task.isSuccessful) {
          val imageUrl = task.result.toString()
          val data = param.data
          data?.link = imageUrl
          param.onSucceeded(arrayListOf(data!!))
        } else {
          param.onFailed("Gagal mendapatkan url gambar")
        }
      }.addOnFailureListener { exc ->
        param.onFailed(exc.message!!)
      }
  }
}