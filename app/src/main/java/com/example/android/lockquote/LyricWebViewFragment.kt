package com.example.android.lockquote

import android.content.ClipboardManager
import android.content.Context
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
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_lyric_webview.lyricSelectionTextView

class LyricWebViewFragment : Fragment() {
    var lyricUrl: String? = null

    companion object {
        fun newInstance(): LyricWebViewFragment {
            return LyricWebViewFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val webViewLayout = inflater.inflate(R.layout.fragment_lyric_webview, container, false)
        val lyricWebView: WebView = webViewLayout.findViewById(R.id.lyricWebView)
        val useSelectionButton = webViewLayout.findViewById<Button>(R.id.useSelectionButton)
        var numberOfWords = 0

        // Enable Javascript
        val webSettings = lyricWebView.settings

        // webSettings.javaScriptEnabled = true
        webSettings.allowContentAccess = true
        webSettings.disabledActionModeMenuItems = MENU_ITEM_WEB_SEARCH

        lyricWebView.webViewClient = WebViewClient()

        val clipboard = activity?.getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.addPrimaryClipChangedListener {
            val regex = Regex("(\\s+|\\\\n)")
            val selectedText = clipboard.primaryClip?.getItemAt(0)
            numberOfWords = selectedText?.text?.split(regex)?.count() ?: 0
            useSelectionButton.text = String.format(context?.resources?.getString(R.string.use_button) + " ($numberOfWords)")

            if (selectedText != null) {
                val lyricSelectionTextView = view?.findViewById<TextView>(R.id.lyricSelectionTextView)
                lyricSelectionTextView?.text = selectedText.text
            } else {
                showError(requireContext(), "Something went wrong.")
            }
        }

        lyricUrl?.let { lyricWebView.loadUrl(it) }
            ?: context?.let { showError(it, "Can't open lyrics") }

        useSelectionButton.text = String.format(resources.getString(R.string.use_button) + " ($numberOfWords)")

        useSelectionButton.setOnClickListener {
            extractSelection(numberOfWords)
        }

        return webViewLayout
    }

    private fun extractSelection(numberOfWords: Int) {
        val selectTextHint = getText(R.string.select_text_hint)
        val selectedText = lyricSelectionTextView
            .text
            .toString()
            .replace("\n", " ")
            .replace("(", "")
            .replace(")", "")
            .replace("\"", "")

        when {
            numberOfWords > 15 -> {
                context?.let {
                    showError(it, "Please select an excerpt of 15 words or less.")
                }
            }
            lyricSelectionTextView.text.contains(selectTextHint) -> {
                context?.let {
                    showError(it, "You haven't selected a lyric excerpt for your password.")
                }
            }
            else -> {
                val intent = Intent(context, GeneratedPasswordActivity::class.java)
                intent.putExtra("selectedText", selectedText)
                startActivity(intent)
            }
        }
    }
}


