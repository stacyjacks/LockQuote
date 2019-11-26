package com.example.android.lockquote

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import android.webkit.WebViewClient


class LyricWebViewFragment(): Fragment() {
    var lyricUrl: String? = null

    companion object {

        fun newInstance(): LyricWebViewFragment {
            return LyricWebViewFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val webViewLayout = inflater.inflate(R.layout.fragment_lyric_webview, container, false)
        val lyricWebView: WebView = webViewLayout.findViewById(R.id.lyricWebView)

        // Enable Javascript
        val webSettings = lyricWebView.getSettings()
        webSettings.javaScriptEnabled = true

        // Force links and redirects to open in the WebView instead of in a browser
        lyricWebView.webViewClient = WebViewClient()

        lyricUrl?.let { lyricWebView.loadUrl(it) }
            ?: context?.let { showError(it, "Can't open lyrics") }

        return webViewLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}


