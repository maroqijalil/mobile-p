package com.example.transactionapp.model

data class TransaksiModel(
  var uang_bayar: Double = 0.0,
  var id_pelanggan: Long = 0L
): Model() {
  override fun getTableName(): String {
    return "transaksi"
  }

  override fun getPrimaryKeyName(): String {
    return "id_transaksi"
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "uang_bayar" to uang_bayar,
      "id_pelanggan" to id_pelanggan
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (${getPrimaryKeyName()} INTEGER PRIMARY KEY, " +
      "id_pelanggan INTEGER, uang_bayar REAL)"
  }
}
