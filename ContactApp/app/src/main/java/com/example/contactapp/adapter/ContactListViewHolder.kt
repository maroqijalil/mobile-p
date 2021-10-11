package com.example.contactapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.databinding.ItemContactBinding
import com.example.contactapp.model.ContactModel

class ContactListViewHolder(private val v: View, private val onItemClick: (ContactModel) -> Unit) :
  RecyclerView.ViewHolder(v) {
  fun bind(item: ContactModel) {
    val binding = ItemContactBinding.bind(v)

    binding.itemCTvName.text = item.nama
    binding.itemCTvNum.text = item.telepon

    binding.itemRlContainer.setOnClickListener { onItemClick(item) }
  }
}
