package com.maroqi.browserapp

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.maroqi.browserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)

    (menu?.findItem(R.id.menu_search)?.actionView as SearchView).apply {
      setSearchableInfo(
        (getSystemService(Context.SEARCH_SERVICE) as SearchManager)
          .getSearchableInfo(componentName)
      )
    }

    return true
  }
}
