package com.example.transactionapp.model

import android.database.Cursor

data class TransaksiModel(
  var id_transaksi: Long = 0L,
  var uang_bayar: Double = 0.0,
  var id_pelanggan: Long = 0L,
  var total_bayar: Double = 0.0,
  var kembalian: Double = 0.0,
  var bonus: String = "",
  var keterangan: String = ""
): Model() {
  override fun getTableName(): String {
    return "transaksi"
  }

  override fun getPrimaryKeyName(): String {
    return "id_transaksi"
  }

  override fun fillWithCursor(cursor: Cursor) {
    id_transaksi = cursor.getLong(getColumnIndex(cursor, "id_transaksi"))
    uang_bayar = cursor.getDouble(getColumnIndex(cursor, "uang_bayar"))
    id_pelanggan = cursor.getLong(getColumnIndex(cursor, "id_pelanggan"))
    total_bayar = cursor.getDouble(getColumnIndex(cursor, "total_bayar"))
    kembalian = cursor.getDouble(getColumnIndex(cursor, "kembalian"))
    bonus = cursor.getString(getColumnIndex(cursor, "bonus"))
    keterangan = cursor.getString(getColumnIndex(cursor, "keterangan"))
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "uang_bayar" to uang_bayar,
      "id_pelanggan" to id_pelanggan,
      "total_bayar" to total_bayar,
      "kembalian" to kembalian,
      "bonus" to bonus,
      "keterangan" to keterangan
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
      "id_pelanggan INTEGER, uang_bayar REAL, total_bayar REAL, kembalian REAL, " +
      "bonus TEXT, keterangan TEXT)"
  }
}
