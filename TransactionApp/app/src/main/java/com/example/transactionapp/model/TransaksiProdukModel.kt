package com.example.transactionapp.model

data class TransaksiProdukModel(
  var id_transaksi: Int = 0,
  var id_produk: Int = 0
): Model() {
  override fun getTableName(): String {
    return "transaksi_produk"
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "id_transaksi" to id_transaksi,
      "id_produk" to id_produk
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (id_transaksi_model INTEGER PRIMARY KEY, " +
      "id_transaksi INTEGER, id_produk INTEGER)"
  }
}
