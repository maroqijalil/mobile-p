package com.example.contactapp.model

import android.database.Cursor

data class ContactModel(
  var id_contact: Long = 0L,
  var nama: String = "",
  var telepon: String = ""
) : Model() {
  override fun getTableName(): String {
    return "contact"
  }

  override fun getPrimaryKeyName(): String {
    return "id_contact"
  }

  override fun fillWithCursor(cursor: Cursor): ContactModel {
    return ContactModel(
      id_contact = cursor.getLong(getColumnIndex(cursor, "id_contact")),
      nama = cursor.getString(getColumnIndex(cursor, "nama")),
      telepon = cursor.getString(getColumnIndex(cursor, "telepon"))
    )
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "nama" to nama,
      "telepon" to telepon
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
      "nama TEXT, telepon TEXT)"
  }
}
