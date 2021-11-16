package com.fiqi.galleryapp.view.adapters.main

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.fiqi.galleryapp.R
import com.fiqi.galleryapp.databinding.ItemImageBinding
import com.fiqi.galleryapp.data.model.ImageModel
import com.squareup.picasso.Picasso

class ImageListViewHolder(private val binding: ItemImageBinding) :
  RecyclerView.ViewHolder(binding.root) {
  fun bind(data: ImageModel) {
    Picasso.get()
      .load(data.link)
      .resize(360, 360)
      .centerCrop()
      .placeholder(R.drawable.ic_baseline_image_24)
      .error(R.drawable.ic_baseline_image_24)
      .into(binding.itemIvImage)
    binding.itemTvTitle.text = data.title
    binding.itemTvDesc.text = data.desc
  }
}
