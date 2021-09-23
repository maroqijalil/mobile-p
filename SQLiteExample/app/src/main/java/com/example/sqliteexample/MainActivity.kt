package com.example.sqliteexample

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqliteexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var myDb: SQLiteDatabase

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    myDb = openOrCreateDatabase("db.sql", MODE_PRIVATE, null)
    myDb.execSQL("create table if not exists mhs(nrp TEXT, nama TEXT);")

    setupButtons()
  }

  override fun onStop() {
    myDb.close()
    super.onStop()
  }

  private fun setupButtons() {
    binding.mainBtnSave.setOnClickListener {
      if (validateInput()) {
        val myData = ContentValues()
        myData.put("nrp", binding.mainEtNrp.text.toString())
        myData.put("nama", binding.mainEtName.text.toString())
        myDb.insert("mhs", null, myData)
        showToast("Data Tersimpan")
      }
    }

    binding.mainBtnGet.setOnClickListener {
      val cursor = myDb.rawQuery(
        "select * from mhs where nrp='${binding.mainEtNrp.text}'", null
      )
      if (cursor.count > 0) {
        cursor.moveToFirst()
        val index = if (cursor.getColumnIndex("nama") >= 0) {
          cursor.getColumnIndex("nama")
        } else {
          0
        }
        binding.mainEtName.setText(cursor.getString(index))

        showToast("Data Ditemukan Sejumlah ${cursor.count}")
      } else {
        showToast("Data Tidak Ditemukan")
      }
    }

    binding.mainBtnUpdate.setOnClickListener {
      if (validateInput()) {
        val myData = ContentValues()
        myData.put("nrp", binding.mainEtNrp.text.toString())
        myData.put("nama", binding.mainEtName.text.toString())
        myDb.update("mhs", myData, "nrp='${binding.mainEtNrp.text}'", null)
        showToast("Data Terupdate")
      }
    }

    binding.mainBtnDelete.setOnClickListener {
      var isValid = true

      if (binding.mainEtNrp.text.isNullOrEmpty()) {
        binding.mainEtNrp.error = "Isian tidak boleh kosong!"
        isValid = false
      }

      if (isValid) {
        myDb.delete("mhs","nrp='${binding.mainEtNrp.text}'", null)
        showToast("Data Terhapus")
      }
    }
  }

  private fun validateInput(): Boolean {
    var isValid = true

    if (binding.mainEtNrp.text.isNullOrEmpty()) {
      binding.mainEtNrp.error = "Isian tidak boleh kosong!"
      isValid = false
    }
    if (binding.mainEtName.text.isNullOrEmpty()) {
      binding.mainEtName.error = "Isian tidak boleh kosong!"
      isValid = false
    }

    return isValid
  }

  private fun showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
  }
}
