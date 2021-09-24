package com.example.transactionapp.screen

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

  var database_log = "TransactionAppDatabase"

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
          database.insert(TransaksiProdukModel(id_produk = id, id_transaksi = idTransaksi))
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
        val db = database.readableDatabase
        val pelanggan = PelangganModel(nama_pelanggan = binding.etNamaPelanggan.text.toString())

        var query = "SELECT * FROM ${pelanggan.getTableName()} " +
          "WHERE nama_pelanggan LIKE '%${pelanggan.nama_pelanggan}%'"

        var cursor = db.rawQuery(query, null)
        if (cursor.count > 0) {
          if (cursor != null) {
            cursor.moveToFirst()
            pelanggan.fillWithCursor(cursor)
          }
        } else {
          showToast("Pengguna tidak ditemukan")
          return@setOnClickListener
        }

        val transaksi = TransaksiModel(id_pelanggan = pelanggan.id_pelanggan)

        query = "SELECT * FROM ${transaksi.getTableName()} " +
          "WHERE id_pelanggan='${transaksi.id_pelanggan}'"

        cursor = db.rawQuery(query, null)
        if (cursor.count > 0) {
          if (cursor != null) {
            cursor.moveToFirst()
            transaksi.fillWithCursor(cursor)
          }
        } else {
          showToast("Transaksi tidak ditemukan")
          return@setOnClickListener
        }

        val transaksiProduk = TransaksiProdukModel(id_transaksi = transaksi.id_transaksi)
        val transaksiProduks = arrayListOf<TransaksiProdukModel>()

        query = "SELECT * FROM ${transaksiProduk.getTableName()} " +
          "WHERE id_transaksi='${transaksiProduk.id_transaksi}'"

        cursor = db.rawQuery(query, null)
        if (cursor.count > 0) {
          if (cursor != null) {
            if (cursor.moveToFirst()) {
              do {
                transaksiProduk.fillWithCursor(cursor)
                transaksiProduks.add(transaksiProduk)
              } while (cursor.moveToNext())
            }
          }
        } else {
          return@setOnClickListener
        }

        val produks = arrayListOf<ProdukModel>()

        transaksiProduks.forEach { tp ->
          val produk = database.read(tp.id_produk, ProdukModel())
          if (produk.size > 0) {
            produks.add(database.read(tp.id_produk, ProdukModel())[0])
          }
        }
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
