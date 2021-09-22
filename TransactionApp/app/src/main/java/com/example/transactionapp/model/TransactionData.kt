package com.example.transactionapp.model

data class TransactionData(
  var namaPelanggan: String = "",
  var namaBarang: String = "",
  var jmlBarang: Int = 0,
  var harga: Double = 0.0,
  var jmlUang: Double = 0.0
) {
  fun getTotalPrice(): Double {
    return jmlBarang * harga
  }

  fun getReturn(): Double {
    return jmlUang - (jmlBarang * harga)
  }

  fun getTransactionSatus(): String {
    return if (getReturn() < 0.0) {
      "Belum lunas dengan kekurangan: Rp. ${getReturn()}"
    } else if (getReturn() > 0.0) {
      "Lunas dengan kembalian: Rp. ${getReturn()}"
    } else {
      "Lunas"
    }
  }

  fun getBonusStatus(): String {
    return if (getTotalPrice() >= 200000) {
      "HardDisk 1TB"
    } else if (getTotalPrice() >= 50000) {
      "Keyboard Gaming"
    } else if (getTotalPrice() >= 40000) {
      "Mouse Gaming"
    } else {
      "Tidak ada bonus!"
    }
  }
}
