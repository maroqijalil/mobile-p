package com.example.transactionapp.db.repository

import com.example.transactionapp.db.master.Database
import com.example.transactionapp.model.PelangganModel

class PelangganRepository(private val db: Database): DatabaseRepository<PelangganModel> {
  override fun insert(model: PelangganModel): Long {
    return db.insert(model)
  }

  override fun read(id: Long): ArrayList<PelangganModel>? {
    TODO("Not yet implemented")
  }

  override fun update(model: PelangganModel, id: Long): Int {
    return db.update(model, id)
  }

  override fun delete(id: Long) {
    db.delete(id, PelangganModel())
  }
}
