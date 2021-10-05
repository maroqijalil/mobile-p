package com.example.contactapp.model

import android.content.ContentValues
import android.database.Cursor

abstract class Model {
  abstract fun getTableName(): String
  abstract fun getPrimaryKeyName(): String
  abstract fun fillWithCursor(cursor: Cursor): Model
  abstract fun toMap(): Map<String, Any>
  abstract fun tableAttr(): String

  fun getColumnIndex(cursor: Cursor, key: String): Int {
    return if (cursor.getColumnIndex(key) >= 0) {
      cursor.getColumnIndex(key)
    } else {
      0
    }
  }

  fun getContentValues(): ContentValues {
    val values = ContentValues()
    val map = toMap()

    map.forEach { (key, value) ->
      when (value) {
        is String -> values.put(key, value)
        is Long -> values.put(key, value)
        is Int -> values.put(key, value)
        is Double -> values.put(key, value)
      }
    }

    return  values
  }
}
