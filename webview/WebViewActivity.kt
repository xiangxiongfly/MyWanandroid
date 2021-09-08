package com.core.wanandroid.webview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.core.app.base.BaseBindingActivity
import com.core.wanandroid.databinding.ActivityWebViewBinding

class WebViewActivity : BaseBindingActivity<ActivityWebViewBinding>() {
    private lateinit var mWebView: WebView

    companion object {
        const val URL = "url"
        const val TITLE = "title"

        fun actionStart(context: Context, url: String? = null, title: String? = null) {
            context.startActivity(Intent(context, WebViewActivity::class.java).apply {
                putExtra(URL, url)
                putExtra(TITLE, title)
            })
        }
    }

    private var mTitle: String? = null
    private var mUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initWebView()
    }

    private fun initData() {
        mTitle = intent.getStringExtra(TITLE)
        mUrl = intent.getStringExtra(URL)

        binding.titleBar.setTitle(mTitle)
    }

    private fun initWebView() {
        mWebView = WebView(mContext)
        val layoutParmas = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        mWebView.layoutParams = layoutParmas
        binding.webviewContainer.addView(mWebView)

        val webSettings = mWebView.settings
        webSettings.domStorageEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.javaScriptEnabled = true


        val webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == null) {
                    return false
                }
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view?.loadUrl(url)
                    return false
                } else {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    return true
                }
            }
        }

        val webChromeClient: WebChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                if (mTitle == null) {
                    mTitle = title
                    binding.titleBar.setTitle(mTitle)
                }
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressBar.isVisible = newProgress != 100
                binding.progressBar.progress = newProgress
            }
        }

        mWebView.webViewClient = webViewClient
        mWebView.webChromeClient = webChromeClient

        mUrl?.let {
            mWebView.loadUrl(it)
        }
    }

    override fun onDestroy() {
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mWebView.clearHistory();
        binding.webviewContainer.removeAllViews()
        mWebView.destroy();
        super.onDestroy()
    }
}