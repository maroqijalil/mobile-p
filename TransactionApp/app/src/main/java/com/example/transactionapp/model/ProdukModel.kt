package com.example.transactionapp.model

data class ProdukModel(
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

  override fun toMap(): Map<String, Any> {
    return mapOf(
      "nama_barang" to nama_barang,
      "jml_barang" to jml_barang,
      "harga" to harga
    )
  }

  override fun tableAttr(): String {
    return "${getTableName()} (id_produk INTEGER PRIMARY KEY, " +
      "nama_barang TEXT, jml_barang INTEGER, harga REAL)"
  }
}
