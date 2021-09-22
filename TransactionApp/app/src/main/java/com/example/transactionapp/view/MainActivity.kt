package com.example.transactionapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transactionapp.adapter.TransactionListAdapter
import com.example.transactionapp.databinding.ActivityMainBinding
import com.example.transactionapp.model.TransactionData

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

      if (binding.etNamaPelanggan.text.isNullOrEmpty()) {
        binding.etNamaPelanggan.error = "Isian tidak boleh kosong"
        isValid = false
      }
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
      if (binding.etJmlUang.text.isNullOrEmpty()) {
        binding.etJmlUang.error = "Isian tidak boleh kosong"
        isValid = false
      }

      if (isValid) {
        val data = TransactionData(
          binding.etNamaPelanggan.text.toString(),
          binding.etNamaBarang.text.toString(),
          binding.etJmlBarang.text.toString().toInt(),
          binding.etHarga.text.toString().toDouble(),
          binding.etJmlUang.text.toString().toDouble()
        )
        adapter.addItem(data)
      }
    }

    binding.mainBtnDeletee.setOnClickListener {
      adapter.clearList()
    }

    binding.mainBtnOut.setOnClickListener {
      moveTaskToBack(true)
    }
  }

  private fun showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
  }
}
