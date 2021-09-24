package com.example.transactionapp.db.master

interface DatabaseRepository<T> {
  fun insert(model: T)
  fun read(id: Int): T?
  fun update(model: T, id: Int)
  fun delete(id: Int)
}
