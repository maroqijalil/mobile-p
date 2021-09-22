package com.example.transactionapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.transactionapp.databinding.ItemMainBinding
import com.example.transactionapp.model.TransactionData

class TransactionListViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
  fun bindItem(data: TransactionData) {
    val binding = ItemMainBinding.bind(v)

    binding.tvNamaBarang.text = data.namaBarang
    binding.tvJmlBarang.text = data.jmlBarang.toString()
    binding.tvHarga.text = data.harga.toString()
  }
}
