package com.example.transactionapp.db.sqlite

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.transactionapp.model.*
import android.content.ContentValues
import android.database.Cursor

class TransactionDatabase(
  context: Context?,
  private val models: ArrayList<Model>,
  name: String? = "transaction_app_database",
  factory: SQLiteDatabase.CursorFactory? = null,
  version: Int = 1,
  errorHandler: DatabaseErrorHandler? = null
) : SQLiteOpenHelper(context, name, factory, version, errorHandler) {
  override fun onCreate(db: SQLiteDatabase?) {
    if (db != null) {
      models.forEach { model ->
        createTable(db, model)
      }
    }
  }

  override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
    if (db != null) {
      models.forEach { model ->
        dropTable(db, model)
      }

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

  fun <T : Model> read(model: T, id: Long?, where: String = ""): ArrayList<T> {
    val db = this.readableDatabase
    val list = arrayListOf<T>()

    var query = "SELECT * FROM ${model.getTableName()}"
    if (id != null) {
      query += " WHERE ${model.getPrimaryKeyName()}='$id'"
    }
    if (where != "") {
      query += " $where"
    }

    val cursor = db.rawQuery(query, null)
    if (cursor.count > 0 && cursor != null) {
      if (cursor.moveToFirst()) {
        do {
          model.fillWithCursor(cursor)
          list.add(model)
        } while (cursor.moveToNext())
      }
    }

    return list
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

  fun delete(model: Model, id: Long) {
    val db = this.writableDatabase
    db.delete(model.getTableName(), "${model.getPrimaryKeyName()} = $id", null)
  }
}
