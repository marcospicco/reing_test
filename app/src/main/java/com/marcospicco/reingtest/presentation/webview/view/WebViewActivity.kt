package com.marcospicco.reingtest.presentation.webview.view

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.marcospicco.reingtest.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity: AppCompatActivity() {

    private lateinit var url: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        setToolbar()

        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    view?.loadUrl(it)
                }
                return true
            }
        }

        resolveParams()

        if (url.isNotEmpty()) {
            webView.loadUrl(url)
        }
    }

    fun setToolbar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white)))

        val actionBar: ActionBar? = actionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""

        val backArrow = resources.getDrawable(R.drawable.back_arrow)
        backArrow.setColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        supportActionBar?.setHomeAsUpIndicator(backArrow);
    }

    fun resolveParams() {
        url = intent.getStringExtra(URL) ?: ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun getIntent(context: Context, url: String): Intent {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(URL, url)
            return intent
        }

        private const val URL = "url"
    }
}