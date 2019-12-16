package com.example.android.lockquote

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings.MENU_ITEM_WEB_SEARCH
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_lyric_webview.*


class LyricWebViewFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val webViewLayout = inflater.inflate(R.layout.fragment_lyric_webview, container, false)
        val lyricWebView: WebView = webViewLayout.findViewById(R.id.lyricWebView)
        val useSelectionButton = webViewLayout.findViewById<Button>(R.id.useSelectionButton)

        // Enable Javascript
        val webSettings = lyricWebView.settings
        // webSettings.javaScriptEnabled = true
        webSettings.allowContentAccess = true
        webSettings.disabledActionModeMenuItems = MENU_ITEM_WEB_SEARCH

        lyricWebView.webViewClient = WebViewClient()

        val clipboard: ClipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.addPrimaryClipChangedListener {
            val selectedText = clipboard.primaryClip.getItemAt(0)
            if (selectedText != null) {
                lyricSelectionTextView.text = selectedText.text
            }
        }

        lyricUrl?.let { lyricWebView.loadUrl(it) }
            ?: context?.let { showError(it, "Can't open lyrics") }

        useSelectionButton.setOnClickListener {
            extractSelection()
        }

        return webViewLayout
    }

    private fun extractSelection() {
        val selectedText = lyricSelectionTextView
            .text
            .toString()
            .replace("\n", " ")
            .replace("(", "")
            .replace(")", "")

        val intent = Intent(context, GeneratedPasswordActivity::class.java)
        intent.putExtra("selectedText", selectedText)
        startActivity(intent)
    }
}


