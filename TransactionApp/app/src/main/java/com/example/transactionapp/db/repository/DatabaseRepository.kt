package com.example.transactionapp.db.repository

interface DatabaseRepository<T> {
  fun insert(model: T): Long
  fun read(id: Long): ArrayList<T>?
  fun update(model: T, id: Long): Int
  fun delete(id: Long)
}
