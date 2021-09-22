package com.example.transactionapp.model

data class TransactionData(
  var namaBarang: String = "",
  var jmlBarang: Int = 0,
  var harga: Double = 0.0,
) {
  fun getTotalPrice(): Double {
    return jmlBarang * harga
  }
}
