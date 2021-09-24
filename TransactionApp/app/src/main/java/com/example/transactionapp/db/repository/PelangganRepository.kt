package com.example.transactionapp.db.repository

import com.example.transactionapp.db.master.Database
import com.example.transactionapp.db.master.DatabaseRepository
import com.example.transactionapp.model.PelangganModel

class PelangganRepository(private val db: Database): DatabaseRepository<PelangganModel> {
  override fun insert(model: PelangganModel) {
    TODO("Not yet implemented")
  }

  override fun read(id: Int): PelangganModel? {
    TODO("Not yet implemented")
  }

  override fun update(model: PelangganModel, id: Int) {
    TODO("Not yet implemented")
  }

  override fun delete(id: Int) {
    TODO("Not yet implemented")
  }
}
