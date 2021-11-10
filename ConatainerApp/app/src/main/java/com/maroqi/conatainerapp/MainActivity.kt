package com.maroqi.conatainerapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maroqi.conatainerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mainBtnMap.setOnClickListener {
      val intent = Uri.parse("map://com.maroqi.mapsapp").let {
        Intent(Intent.ACTION_VIEW, it)
      }
      startActivity(intent)
    }

    binding.mainBtnContact.setOnClickListener {
      val intent = Uri.parse("con://com.maroqi.contactapp").let {
        Intent(Intent.ACTION_VIEW, it)
      }
      startActivity(intent)
    }

    binding.mainBtnBrowser.setOnClickListener {
      val intent = Uri.parse("web://com.maroqi.browserapp").let {
        Intent(Intent.ACTION_VIEW, it)
      }
      startActivity(intent)
    }
  }
}
