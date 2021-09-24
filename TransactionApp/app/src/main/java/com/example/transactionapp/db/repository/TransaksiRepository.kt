package com.example.transactionapp.db.repository

import com.example.transactionapp.db.master.Database
import com.example.transactionapp.model.PelangganModel
import com.example.transactionapp.model.TransaksiModel

class TransaksiRepository(private val db: Database): DatabaseRepository<TransaksiModel> {
  override fun insert(model: TransaksiModel): Long {
    return db.insert(model)
  }

  override fun read(id: Long): ArrayList<TransaksiModel>? {
    TODO("Not yet implemented")
  }

  override fun update(model: TransaksiModel, id: Long): Int {
    return db.update(model, id)
  }

  override fun delete(id: Long) {
    db.delete(id, TransaksiModel())
  }
}
