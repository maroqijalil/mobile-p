package com.fiqi.galleryapp.view.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiqi.galleryapp.data.model.Image

class MainViewModel: ViewModel() {
  private val _images = MutableLiveData<ArrayList<Image>>()

  fun getImages(): LiveData<ArrayList<Image>> = _images

  fun getImagesData() {

  }
}
