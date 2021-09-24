package com.example.transactionapp.model

import android.database.Cursor

abstract class Model {
  abstract fun getTableName(): String
  abstract fun getPrimaryKeyName(): String
  abstract fun fillWithCursor(cursor: Cursor)
  abstract fun toMap(): Map<String, Any>
  abstract fun tableAttr(): String

  fun getColumnIndex(cursor: Cursor, key: String): Int {
    return if (cursor.getColumnIndex(key) >= 0) {
      cursor.getColumnIndex(key)
    } else {
      0
    }
  }
}
