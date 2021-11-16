package com.fiqi.galleryapp.view.adapters.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fiqi.galleryapp.databinding.ItemImageBinding
import com.fiqi.galleryapp.data.model.Image

class ImageListAdapter : RecyclerView.Adapter<ImageListViewHolder>() {

  private val _datas: ArrayList<Image> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
    val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ImageListViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
    holder.bind(_datas[position])
  }

  override fun getItemCount(): Int {
    return _datas.size
  }

  fun changeList(datas: ArrayList<Image>) {
    if (_datas.size > 0) {
      _datas.clear()
    }

    _datas.addAll(datas)
  }
}
