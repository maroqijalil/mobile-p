package com.example.transactionapp.db.master

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.transactionapp.model.*

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
}
