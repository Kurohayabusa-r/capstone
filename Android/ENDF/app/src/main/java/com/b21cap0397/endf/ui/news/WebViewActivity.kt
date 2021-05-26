package com.b21cap0397.endf.ui.news

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.b21cap0397.endf.R

class WebViewActivity : AppCompatActivity() {

    companion object {
        const val URL = "url"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val extras = intent.extras

        if (extras != null) {
            val url = extras.getString(URL)
            if (url != null) {
                showWebView(url)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showWebView(url: String) {
        val wvNews = findViewById<WebView>(R.id.wv_news)
        wvNews.webViewClient = WebViewClient()
        wvNews.apply {
            loadUrl(url)
        }
    }
}