package com.example.transactionapp.model

import android.database.Cursor

data class ProdukModel(
  var id_produk: Long = 0L,
  var nama_barang: String = "",
  var jml_barang: Int = 0,
  var harga: Double = 0.0,
) : Model() {
  fun getTotalPrice(): Double {
    return jml_barang * harga
  }

  override fun getTableName(): String {
    return "produk"
  }

  override fun getPrimaryKeyName(): String {
    return "id_produk"
  }

  override fun fillWithCursor(cursor: Cursor) {
    id_produk = cursor.getLong(getColumnIndex(cursor, "id_produk"))
    nama_barang = cursor.getString(getColumnIndex(cursor, "nama_barang"))
    jml_barang = cursor.getInt(getColumnIndex(cursor, "jml_barang"))
    harga = cursor.getDouble(getColumnIndex(cursor, "harga"))
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "nama_barang" to nama_barang,
      "jml_barang" to jml_barang,
      "harga" to harga
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
      "nama_barang TEXT, jml_barang INTEGER, harga REAL)"
  }
}
