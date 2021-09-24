package com.example.transactionapp.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transactionapp.adapter.TransactionListAdapter
import com.example.transactionapp.databinding.ActivityMainBinding
import com.example.transactionapp.model.ProdukModel

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private lateinit var adapter: TransactionListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)

    setContentView(binding.root)

    setupButtons()

    adapter = TransactionListAdapter()
    binding.mainRvItem.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = this@MainActivity.adapter
    }
  }

  private fun setupButtons() {
    binding.mainBtnProcess.setOnClickListener {
      var isValid = true

      if (binding.etNamaBarang.text.isNullOrEmpty()) {
        binding.etNamaBarang.error = "Isian tidak boleh kosong"
        isValid = false
      }
      if (binding.etJmlBarang.text.isNullOrEmpty()) {
        binding.etJmlBarang.error = "Isian tidak boleh kosong"
        isValid = false
      }
      if (binding.etHarga.text.isNullOrEmpty()) {
        binding.etHarga.error = "Isian tidak boleh kosong"
        isValid = false
      }

      if (isValid) {
        val data = ProdukModel(
          binding.etNamaBarang.text.toString(),
          binding.etJmlBarang.text.toString().toInt(),
          binding.etHarga.text.toString().toDouble(),
        )
        adapter.addItem(data)

        binding.tvTotal.text = getTotalPrice(adapter.getList()).toString()
      }
    }

    binding.mainBtnDeletee.setOnClickListener {
      adapter.clearList()
    }

    binding.mainBtnPay.setOnClickListener {
      var isValid = true

      if (binding.etNamaPelanggan.text.isNullOrEmpty()) {
        binding.etNamaPelanggan.error = "Isian tidak boleh kosong"
        isValid = false
      }
      if (binding.etJmlUang.text.isNullOrEmpty()) {
        binding.etJmlUang.error = "Isian tidak boleh kosong"
        isValid = false
      }

      if (isValid) {
        val total = getTotalPrice(adapter.getList())
        val retur = binding.etJmlUang.text.toString().toDouble() - total

        binding.tvKembalian.text = if (retur >= 0.0) {
          retur.toString()
        } else {
          "-"
        }

        binding.tvBonus.text = if (total >= 200000) {
          "HardDisk 1TB"
        } else if (total >= 50000) {
          "Keyboard Gaming"
        } else if (total >= 40000) {
          "Mouse Gaming"
        } else {
          "Tidak ada bonus!"
        }

        binding.tvKeterangan.text = if (retur < 0.0) {
          "Belum lunas dengan kekurangan: Rp. $retur"
        } else if (retur > 0.0) {
          "Lunas dengan kembalian: Rp. $retur"
        } else {
          "Lunas"
        }
      }
    }
  }

  fun getTotalPrice(produks: ArrayList<ProdukModel>): Double {
    var total = 0.0
    produks.forEach { data ->
      total += data.getTotalPrice()
    }
    return total
  }
}
