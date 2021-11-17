package com.fiqi.galleryapp.view.viewmodels.main

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiqi.galleryapp.data.db.firebase.firestore.GalleryFirestore
import com.fiqi.galleryapp.data.db.firebase.storage.GalleryStorage
import com.fiqi.galleryapp.data.model.FileModel
import com.fiqi.galleryapp.data.model.ImageModel
import com.fiqi.galleryapp.data.params.SuperParams
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
  private val galleryFb = GalleryFirestore()
  private val storageRepository = GalleryStorage()

  private val _images = MutableLiveData<ArrayList<ImageModel>>()

  fun getImages(): LiveData<ArrayList<ImageModel>> = _images

  fun getImagesData() {
    galleryFb.read(SuperParams(
      onSucceeded = { data ->
        _images.value = data
      },
      onFailed = ::setfFailedMessage
    ))
  }

  fun insertImagesData(title: String, imageUri: Uri, imageFormat: String, desc: String) {
    var file: FileModel<Uri> = FileModel<Uri>()

    CoroutineScope(Dispatchers.Main + Job()).launch {
      storageRepository.uploadFile(
        SuperParams(
          data = FileModel(
            file = imageUri,
            name = title,
            format = imageFormat
          ),
          onSucceeded = { data ->
            file = data!![0]
            galleryFb.insert(SuperParams(data = ImageModel(
              title = title,
              link = file.link,
              desc = desc
            ), onSucceeded = {}, onFailed = {})
            )
          },
          onFailed = {}
        )
      )
    }
  }

  fun deleteImagesData(id: String) {
    galleryFb.delete(SuperParams(data = id, onSucceeded = {}, onFailed = {}))
  }

  private val _image = MutableLiveData<ImageModel>()

  fun getImage(): LiveData<ImageModel> = _image

  fun setImageData(data: ImageModel) = _image.postValue(data)

  private val _datestamp = MutableLiveData<String>()

  fun setDatestampData(datestamp: String) = _datestamp.postValue(datestamp)

  fun getDatestamp(): LiveData<String> = _datestamp

  private val _imageUri = MutableLiveData<Uri>()

  fun setImageUriData(imageUri: Uri) = _imageUri.postValue(imageUri)

  fun getImageUri(): LiveData<Uri> = _imageUri

  private val _failedMessage = MutableLiveData<String>()

  fun setfFailedMessage(message: String) = _failedMessage.postValue(message)

  fun getFailedMessage(): LiveData<String> = _failedMessage
}
