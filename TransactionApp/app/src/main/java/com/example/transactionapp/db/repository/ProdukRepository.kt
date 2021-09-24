package com.example.transactionapp.db.repository

import com.example.transactionapp.db.master.Database
import com.example.transactionapp.model.PelangganModel
import com.example.transactionapp.model.ProdukModel

class ProdukRepository(private val db: Database): DatabaseRepository<ProdukModel> {
  override fun insert(model: ProdukModel): Long {
    return db.insert(model)
  }

  override fun read(id: Long): ArrayList<ProdukModel>? {
    TODO("Not yet implemented")
  }

  override fun update(model: ProdukModel, id: Long): Int {
    return db.update(model, id)
  }

  override fun delete(id: Long) {
    db.delete(id, ProdukModel())
  }
}
