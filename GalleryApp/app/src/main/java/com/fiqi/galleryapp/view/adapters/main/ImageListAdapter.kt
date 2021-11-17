package com.fiqi.galleryapp.view.adapters.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fiqi.galleryapp.databinding.ItemImageBinding
import com.fiqi.galleryapp.data.model.ImageModel

class ImageListAdapter(private val onItemClick: (ImageModel) -> Unit = {}) :
  RecyclerView.Adapter<ImageListViewHolder>() {

  private val _data: ArrayList<ImageModel> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
    val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ImageListViewHolder(binding, onItemClick)
  }

  override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
    holder.bind(_data[position])
  }

  override fun getItemCount(): Int {
    return _data.size
  }

  fun changeList(data: ArrayList<ImageModel>) {
    if (_data.size > 0) {
      _data.clear()
    }

    _data.addAll(data)
    notifyDataSetChanged()
  }
}
