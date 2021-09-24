package com.example.transactionapp.db.repository

import com.example.transactionapp.db.master.Database
import com.example.transactionapp.db.master.DatabaseRepository
import com.example.transactionapp.model.ProdukModel

class ProdukRepository(private val db: Database): DatabaseRepository<ProdukModel> {
  override fun insert(model: ProdukModel) {
    TODO("Not yet implemented")
  }

  override fun read(id: Int): ProdukModel? {
    TODO("Not yet implemented")
  }

  override fun update(model: ProdukModel, id: Int) {
    TODO("Not yet implemented")
  }

  override fun delete(id: Int) {
    TODO("Not yet implemented")
  }
}
