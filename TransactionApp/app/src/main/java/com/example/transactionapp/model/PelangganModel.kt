package com.example.transactionapp.model

import android.database.Cursor

data class PelangganModel(
  var id_pelanggan: Long = 0L,
  var nama_pelanggan: String = ""
) : Model() {
  override fun getTableName(): String {
    return "pelanggan"
  }

  override fun getPrimaryKeyName(): String {
    return "id_pelanggan"
  }

  override fun fillWithCursor(cursor: Cursor): PelangganModel {
    return PelangganModel(
      id_pelanggan = cursor.getLong(getColumnIndex(cursor, "id_pelanggan")),
      nama_pelanggan = cursor.getString(getColumnIndex(cursor, "nama_pelanggan"))
    )
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "nama_pelanggan" to nama_pelanggan
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
      "nama_pelanggan TEXT)"
  }
}
