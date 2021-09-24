package com.example.transactionapp.model

data class PelangganModel(
  var nama_pelanggan: String = ""
): Model() {
  override fun getTableName(): String {
    return "pelanggan"
  }

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "nama_pelanggan" to nama_pelanggan
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (id_pelanggan INTEGER PRIMARY KEY, " +
      "nama_pelanggan TEXT)"
  }
}
