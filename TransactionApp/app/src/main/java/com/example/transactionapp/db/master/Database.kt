package com.example.transactionapp.db.master

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.transactionapp.model.*
import android.content.ContentValues
import android.database.Cursor

class Database(
  context: Context?,
  name: String?,
  factory: SQLiteDatabase.CursorFactory? = null,
  version: Int = 1,
  errorHandler: DatabaseErrorHandler? = null
) : SQLiteOpenHelper(context, name, factory, version, errorHandler) {
  override fun onCreate(db: SQLiteDatabase?) {
    if (db != null) {
      createTable(db, PelangganModel())
      createTable(db, ProdukModel())
      createTable(db, TransaksiModel())
      createTable(db, TransaksiProdukModel())
    }
  }

  override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
    if (db != null) {
      dropTable(db, PelangganModel())
      dropTable(db, ProdukModel())
      dropTable(db, TransaksiModel())
      dropTable(db, TransaksiProdukModel())

      onCreate(db)
    }
  }

  private fun createTable(db: SQLiteDatabase, model: Model) {
    db.execSQL("CREATE TABLE ${model.tableAttr()}")
  }

  private fun dropTable(db: SQLiteDatabase, model: Model) {
    db.execSQL("DROP TABLE IF EXISTS ${model.getTableName()}")
  }

  fun insert(model: Model): Long {
    val db = this.writableDatabase
    val values = ContentValues()
    val map = model.toMap()

    map.forEach { (key, value) ->
      when (value) {
        is String -> values.put(key, value)
        is Long -> values.put(key, value)
        is Int -> values.put(key, value)
        is Double -> values.put(key, value)
      }
    }

    return db.insert(model.getTableName(), null, values)
  }

  fun read(model: Model, id: Long?): Cursor? {
    val db = this.readableDatabase
    val query = if (id != null) {
      "SELECT * FROM ${model.getTableName()} " +
        "WHERE ${model.getPrimaryKeyName()} = $id"
    } else {
      "SELECT * FROM ${model.getTableName()}"
    }

    return db.rawQuery(query, null)
  }

  fun update(model: Model, id: Long?): Int {
    val db = this.writableDatabase
    val values = ContentValues()
    val map = model.toMap()

    map.forEach { (key, value) ->
      when (value) {
        is String -> values.put(key, value)
        is Long -> values.put(key, value)
        is Int -> values.put(key, value)
        is Double -> values.put(key, value)
      }
    }

    return db.update(
      model.getTableName(),
      values,
      "${model.getPrimaryKeyName()} = $id",
      null
    )
  }

  fun delete(id: Long, model: Model) {
    val db = this.writableDatabase
    db.delete(model.getTableName(),"${model.getPrimaryKeyName()} = $id",null)
  }
}
