package com.example.transactionapp.db.repository

import com.example.transactionapp.db.master.Database
import com.example.transactionapp.db.master.DatabaseRepository
import com.example.transactionapp.model.TransaksiModel

class TransaksiRepository(private val db: Database): DatabaseRepository<TransaksiModel> {
  override fun insert(model: TransaksiModel) {
    TODO("Not yet implemented")
  }

  override fun read(id: Int): TransaksiModel? {
    TODO("Not yet implemented")
  }

  override fun update(model: TransaksiModel, id: Int) {
    TODO("Not yet implemented")
  }

  override fun delete(id: Int) {
    TODO("Not yet implemented")
  }
}
