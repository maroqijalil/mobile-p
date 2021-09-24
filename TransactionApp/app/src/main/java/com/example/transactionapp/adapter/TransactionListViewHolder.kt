package com.example.transactionapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.transactionapp.databinding.ItemMainBinding
import com.example.transactionapp.model.ProdukModel

class TransactionListViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
  fun bindItem(data: ProdukModel) {
    val binding = ItemMainBinding.bind(v)

    binding.tvNamaBarang.text = data.nama_barang
    binding.tvJmlBarang.text = data.jml_barang.toString()
    binding.tvHarga.text = data.harga.toString()
  }
}
