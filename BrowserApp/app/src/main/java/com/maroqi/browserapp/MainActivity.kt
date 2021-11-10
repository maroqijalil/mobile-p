package com.maroqi.browserapp

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import android.widget.SearchView
import com.maroqi.browserapp.databinding.ActivityMainBinding
import android.webkit.WebView
import androidx.core.view.MenuItemCompat


class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private var lastSearchUrl: String = "https://www.google.com/"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mainSrl.setOnRefreshListener {
      searchUrl(lastSearchUrl)
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
          val url = if (query.startsWith("http://") || query.startsWith("https://")) {
            searchUrl(query)
            query
          } else {
            searchUrl("http://$query")
            "http://$query"
          }
          lastSearchUrl = url
        }
        return false
      }

      override fun onQueryTextChange(query: String?): Boolean {
        return false
      }
    })

    return true
  }

  private fun setupWeb() {
    binding.mainWv.apply {
      settings.javaScriptEnabled = true
      webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
          binding.mainSrl.isRefreshing = false
        }

        override fun onReceivedError(
          view: WebView?,
          request: WebResourceRequest?,
          error: WebResourceError?
        ) {
          if (lastSearchUrl.startsWith("http://")) {
            searchUrl("https://${lastSearchUrl.substringAfter("http://")}")
            lastSearchUrl = "https://${lastSearchUrl.substringAfter("http://")}"
          } else {
            super.onReceivedError(view, request, error)
          }
        }
      }
    }

    searchUrl(lastSearchUrl)
  }

  private fun searchUrl(url: String = "https://www.google.com/") {
    binding.mainSrl.isRefreshing = true
    binding.mainWv.loadUrl(url)
  }
}
