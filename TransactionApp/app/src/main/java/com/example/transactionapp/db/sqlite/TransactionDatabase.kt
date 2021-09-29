package com.example.transactionapp.db.sqlite

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.transactionapp.model.*

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
    return this.writableDatabase.insert(
      model.getTableName(),
      null,
      model.getContentValues()
    )
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
    if (cursor != null && cursor.count > 0) {
      if (cursor.moveToFirst()) {
        do {
          val newModel = model.fillWithCursor(cursor)
          list.add(newModel as T)
        } while (cursor.moveToNext())
      }
    }

    return list
  }

  fun update(model: Model, id: Long?): Int {
    return this.writableDatabase.update(
      model.getTableName(),
      model.getContentValues(),
      "${model.getPrimaryKeyName()} = $id",
      null
    )
  }

  fun delete(model: Model, id: Long) {
    this.writableDatabase.delete(
      model.getTableName(), "${model.getPrimaryKeyName()} = $id", null
    )
  }
}
