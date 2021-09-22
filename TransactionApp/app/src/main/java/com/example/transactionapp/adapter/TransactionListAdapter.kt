package com.example.transactionapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transactionapp.databinding.ItemMainBinding
import com.example.transactionapp.model.TransactionData

class TransactionListAdapter : RecyclerView.Adapter<TransactionListViewHolder>() {

  private var transactions = arrayListOf<TransactionData>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
    return TransactionListViewHolder(
      ItemMainBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ).root
    )
  }

  override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
    holder.bindItem(transactions[position])
  }

  override fun getItemCount(): Int {
    return transactions.size
  }

  fun addItem(data: TransactionData) {
    transactions.add(data)
    notifyDataSetChanged()
  }

  fun clearList() {
    transactions.clear()
    notifyDataSetChanged()
  }

  fun getList(): ArrayList<TransactionData> {
    return transactions
  }
}
