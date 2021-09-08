package com.core.wanandroid.webview

import com.core.app.base.BaseBindingActivity
import com.core.wanandroid.databinding.ActivityWebViewBinding

class AgentWebActivity : BaseBindingActivity<ActivityWebViewBinding>() {
//
//    companion object {
//        const val URL = "url"
//        const val TITLE = "title"
//
//        fun actionStart(context: Context, url: String? = null, title: String? = null) {
//            context.startActivity(Intent(context, AgentWebActivity::class.java).apply {
//                putExtra(URL, url)
//                putExtra(TITLE, title)
//            })
//        }
//    }
//
////    private lateinit var mWebView: WebView
//
//    private lateinit var mAgentWeb: AgentWeb
//
//    private var mTitle: String? = null
//    private var mUrl: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initData()
//        initWebView()
//        initSettings()
//    }
//
//    private fun initData() {
//        mTitle = intent.getStringExtra(TITLE)
//        mUrl = intent.getStringExtra(URL)
//
//        binding.titleBar.setTitle(mTitle)
//    }
//
//    private fun initWebView() {
////        mWebView = WebView(mContext)
////        val layoutParmas = FrameLayout.LayoutParams(
////            FrameLayout.LayoutParams.MATCH_PARENT,
////            FrameLayout.LayoutParams.MATCH_PARENT
////        )
////        mWebView.layoutParams = layoutParmas
////        binding.webviewContainer.addView(mWebView)
//
//        val webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                if (url == null) {
//                    return false
//                }
//                if (url.startsWith("weixin://") || url.startsWith("jianshu://")) {
//                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
//                    return true
//                }
//                view?.loadUrl(url)
//                return true
//            }
//        }
//
//        val webChromeClient: WebChromeClient = object : WebChromeClient() {
//            override fun onReceivedTitle(view: WebView?, title: String?) {
//                super.onReceivedTitle(view, title)
//                if (mTitle == null) {
//                    mTitle = title
//                    binding.titleBar.setTitle(mTitle)
//                }
//            }
//
//            override fun onProgressChanged(view: WebView?, newProgress: Int) {
//                super.onProgressChanged(view, newProgress)
//                binding.progressBar.isVisible = newProgress != 100
//                binding.progressBar.progress = newProgress
//            }
//        }
//
////        mAgentWeb = AgentWeb.with(this)
////            .setAgentWebParent(
////                binding.webviewContainer,
////                ViewGroup.LayoutParams(-1, -1)
////            )
////            .useDefaultIndicator()
////            .setWebViewClient(webChromeClient)
////            .setWebChromeClient(webChromeClient)
////            .createAgentWeb()
////            .ready()
////            .go(mUrl)
//    }
//
//    private fun initSettings() {
//
//        val webSettings = mAgentWeb.agentWebSettings.webSettings
//
//        webSettings.domStorageEnabled = true
//        webSettings.useWideViewPort = true
//        webSettings.loadWithOverviewMode = true
//        webSettings.javaScriptEnabled = true
//
////        mUrl?.let {
////            mWebView.loadUrl(it)
////        }
//    }
//
//    override fun onResume() {
//        mAgentWeb.webLifeCycle.onResume()
//        super.onResume()
//    }
//
//    override fun onPause() {
//        mAgentWeb.webLifeCycle.onPause()
//        super.onPause()
//    }
//
//
//    override fun onDestroy() {
////        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
////        mWebView.clearHistory()
////        mWebView.destroy()
////        binding.webviewContainer.removeAllViews()
//
//        mAgentWeb.webLifeCycle.onDestroy()
//        AgentWebConfig.clearDiskCache(BaseApp.INSTANCE);
//        super.onDestroy()
//    }
}