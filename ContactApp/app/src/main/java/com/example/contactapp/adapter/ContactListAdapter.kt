package com.example.contactapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.databinding.ItemContactBinding
import com.example.contactapp.model.ContactModel

class ContactListAdapter(private val onItemClick: (ContactModel) -> Unit) :
  RecyclerView.Adapter<ContactListViewHolder>() {

  private val list = arrayListOf<ContactModel>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
    return ContactListViewHolder(
      ItemContactBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ).root,
      onItemClick
    )
  }

  override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
    holder.bind(list[position])
  }

  override fun getItemCount(): Int {
    return list.size
  }

  fun changeList(data: ArrayList<ContactModel>) {
    if (list.size > 0) {
      list.clear()
    }

    list.addAll(data)
    notifyDataSetChanged()
  }

  fun addItem(item: ContactModel) {
    list.add(item)
    notifyDataSetChanged()
  }

  fun clearList() {
    list.clear()
    notifyDataSetChanged()
  }
}
