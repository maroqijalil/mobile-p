package com.example.transactionapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.transactionapp.databinding.ItemMainBinding
import com.example.transactionapp.model.ProdukModel

class TransactionListAdapter : RecyclerView.Adapter<TransactionListViewHolder>() {

  private var transactions = arrayListOf<ProdukModel>()

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

  fun changeList(datas: ArrayList<ProdukModel>) {
    if (transactions.size > 0) {
      transactions.clear()
    }

    transactions.addAll(datas)
    notifyDataSetChanged()
  }

  fun addItem(data: ProdukModel) {
    transactions.add(data)
    notifyDataSetChanged()
  }

  fun clearList() {
    transactions.clear()
    notifyDataSetChanged()
  }

  fun getList(): ArrayList<ProdukModel> {
    return transactions
  }
}
