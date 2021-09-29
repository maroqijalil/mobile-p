package com.example.transactionapp.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transactionapp.adapter.TransactionListAdapter
import com.example.transactionapp.databinding.ActivityMainBinding
import com.example.transactionapp.db.sqlite.TransactionDatabase
import com.example.transactionapp.model.*

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private lateinit var adapter: TransactionListAdapter

  private lateinit var database: TransactionDatabase

  companion object {
    const val database_log = "TransactionAppDatabase"
  }

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

    database = TransactionDatabase(
      this, arrayListOf(
        PelangganModel(), ProdukModel(), TransaksiModel(), TransaksiProdukModel()
      )
    )
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
          nama_barang = binding.etNamaBarang.text.toString(),
          jml_barang = binding.etJmlBarang.text.toString().toInt(),
          harga = binding.etHarga.text.toString().toDouble(),
        )
        adapter.addItem(data)

        binding.tvTotal.text = getTotalPrice(adapter.getList()).toString()
      }
    }

    binding.mainBtnDeletee.setOnClickListener {
      adapter.clearList()

      binding.tvKeterangan.text = ""
      binding.tvBonus.text = ""
      binding.tvKembalian.text = ""
      binding.tvTotal.text = ""
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
        val produks = adapter.getList()
        val total = getTotalPrice(produks)
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

        val idPelanggan = database.insert(
          PelangganModel(nama_pelanggan = binding.etNamaPelanggan.text.toString())
        )

        val idTransaksi = database.insert(
          TransaksiModel(
            uang_bayar = binding.etJmlUang.text.toString().toDouble(),
            id_pelanggan = idPelanggan,
            total_bayar = total,
            kembalian = retur,
            bonus = binding.tvBonus.text.toString(),
            keterangan = binding.tvKeterangan.text.toString()
          )
        )

        val idProduks = arrayListOf<Long>()
        produks.forEach { produk ->
          idProduks.add(database.insert(produk))
        }

        idProduks.forEach { id ->
          val idTP = database.insert(TransaksiProdukModel(id_produk = id, id_transaksi = idTransaksi))
        }
      }
    }

    binding.mainBtnGetName.setOnClickListener {
      var isValid = true

      if (binding.etNamaPelanggan.text.isNullOrEmpty()) {
        binding.etNamaPelanggan.error = "Isian tidak boleh kosong"
        isValid = false
      }

      if (isValid) {
        val pelanggan: PelangganModel
        var result: Any?
        result = database.read(
          PelangganModel(),
          null,
          "WHERE nama_pelanggan LIKE '%${binding.etNamaPelanggan.text}%'"
        )
        if (result.size > 0) {
          pelanggan = result[0]
        } else {
          showToast("Pengguna tidak ditemukan")
          return@setOnClickListener
        }

        val transaksi: TransaksiModel
        result = database.read(
          TransaksiModel(),
          null,
          "WHERE id_pelanggan='${pelanggan.id_pelanggan}'"
        )
        if (result.size > 0) {
          transaksi = result[0]
        } else {
          showToast("Transaksi tidak ditemukan")
          return@setOnClickListener
        }

        binding.etJmlUang.setText(transaksi.uang_bayar.toString())
        binding.tvTotal.text = transaksi.total_bayar.toString()
        binding.tvKembalian.text = transaksi.kembalian.toString()
        binding.tvBonus.text = transaksi.bonus
        binding.tvKeterangan.text = transaksi.keterangan

        val transaksiProduks = arrayListOf<TransaksiProdukModel>()
        result = database.read(
          TransaksiProdukModel(),
          null,
          "WHERE id_transaksi='${transaksi.id_transaksi}'"
        )
        if (result.size > 0) {
          result.forEach { tP ->
            transaksiProduks.add(tP)
          }
        } else {
          return@setOnClickListener
        }

        val produks = arrayListOf<ProdukModel>()
        transaksiProduks.forEach { tp ->
          val produk = database.read(ProdukModel(), tp.id_produk)
          if (produk.size > 0) {
            produks.add(database.read(ProdukModel(), tp.id_produk)[0])
          }
        }

        adapter.changeList(produks)
      }
    }
  }

  private fun getTotalPrice(produks: ArrayList<ProdukModel>): Double {
    var total = 0.0
    produks.forEach { data ->
      total += data.getTotalPrice()
    }
    return total
  }

  private fun showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
  }
}
