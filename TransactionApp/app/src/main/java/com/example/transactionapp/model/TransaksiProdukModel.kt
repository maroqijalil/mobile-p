package com.example.transactionapp.model

data class TransaksiProdukModel(
  var id_transaksi: Long = 0L,
  var id_produk: Long = 0L
): Model() {
  override fun getTableName(): String {
    return "transaksi_produk"
  }

  override fun getPrimaryKeyName(): String {
    return "id_transaksi_model"
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
