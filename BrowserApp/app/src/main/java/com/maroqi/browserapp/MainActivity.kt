package com.maroqi.browserapp

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.webkit.WebViewClient
import android.widget.SearchView
import com.maroqi.browserapp.databinding.ActivityMainBinding
import android.webkit.WebView
import androidx.core.view.MenuItemCompat


class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mainSrl.setOnRefreshListener {
      setupWeb()
    }

    setupWeb()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)

    val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView

    searchView.apply {
      setSearchableInfo(
        (getSystemService(Context.SEARCH_SERVICE) as SearchManager)
          .getSearchableInfo(componentName)
      )
    }

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
          if (query.startsWith("http://") || query.startsWith("https://")) {
            setupWeb(query)
          } else {
            setupWeb("http://$query")
          }
        }
        return false
      }

      override fun onQueryTextChange(query: String?): Boolean {
        return false
      }
    })

    return true
  }

  private fun setupWeb(url: String = "https://www.google.com/") {
    binding.mainSrl.isRefreshing = true

    binding.mainWv.apply {
      settings.apply {
        javaScriptEnabled = true
      }
      loadUrl(url)
      webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
          binding.mainSrl.isRefreshing = false
        }
      }
    }
  }
}
