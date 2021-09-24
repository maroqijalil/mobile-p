package com.example.transactionapp.db.repository

import com.example.transactionapp.db.master.Database
import com.example.transactionapp.db.master.DatabaseRepository
import com.example.transactionapp.model.TransaksiProdukModel

class TransaksiProdukRepository(private val db: Database): DatabaseRepository<TransaksiProdukModel> {
  override fun insert(model: TransaksiProdukModel) {
    TODO("Not yet implemented")
  }

  override fun read(id: Int): TransaksiProdukModel? {
    TODO("Not yet implemented")
  }

  override fun update(model: TransaksiProdukModel, id: Int) {
    TODO("Not yet implemented")
  }

  override fun delete(id: Int) {
    TODO("Not yet implemented")
  }
}
