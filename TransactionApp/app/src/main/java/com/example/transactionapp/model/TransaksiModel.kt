package com.example.transactionapp.model

data class TransaksiModel(
  var uang_bayar: Double = 0.0,
  var id_pelanggan: Int = 0
): Model() {
  override fun getTableName(): String {
    return "transaksi"
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "uang_bayar" to uang_bayar,
      "id_pelanggan" to id_pelanggan
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (id_transaksi INTEGER PRIMARY KEY, " +
      "id_pelanggan INTEGER, uang_bayar REAL)"
  }
}
