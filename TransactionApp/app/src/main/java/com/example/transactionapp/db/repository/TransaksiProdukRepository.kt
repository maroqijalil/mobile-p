package com.example.transactionapp.db.repository

import com.example.transactionapp.db.master.Database
import com.example.transactionapp.model.PelangganModel
import com.example.transactionapp.model.TransaksiProdukModel

class TransaksiProdukRepository(private val db: Database):
  DatabaseRepository<TransaksiProdukModel> {
  override fun insert(model: TransaksiProdukModel): Long {
    return db.insert(model)
  }

  override fun read(id: Long): ArrayList<TransaksiProdukModel>? {
    TODO("Not yet implemented")
  }

  override fun update(model: TransaksiProdukModel, id: Long): Int {
    return db.update(model, id)
  }

  override fun delete(id: Long) {
    db.delete(id, TransaksiProdukModel())
  }
}
