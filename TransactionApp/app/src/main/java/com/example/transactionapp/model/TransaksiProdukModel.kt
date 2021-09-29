package com.example.transactionapp.model

import android.database.Cursor

data class TransaksiProdukModel(
  var id_transaksi_produk: Long = 0L,
  var id_transaksi: Long = 0L,
  var id_produk: Long = 0L
) : Model() {
  override fun getTableName(): String {
    return "transaksi_produk"
  }

  override fun getPrimaryKeyName(): String {
    return "id_transaksi_produk"
  }

  override fun fillWithCursor(cursor: Cursor): TransaksiProdukModel {
    return TransaksiProdukModel(
      id_transaksi_produk = cursor.getLong(getColumnIndex(cursor, "id_transaksi_produk")),
      id_transaksi = cursor.getLong(getColumnIndex(cursor, "id_transaksi")),
      id_produk = cursor.getLong(getColumnIndex(cursor, "id_produk"))
    )
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "id_transaksi" to id_transaksi,
      "id_produk" to id_produk
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
      "id_transaksi INTEGER, id_produk INTEGER)"
  }
}
