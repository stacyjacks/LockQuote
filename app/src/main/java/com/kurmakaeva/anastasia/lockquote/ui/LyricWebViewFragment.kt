package com.kurmakaeva.anastasia.lockquote.ui

import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings.MENU_ITEM_WEB_SEARCH
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentLyricWebviewBinding
import com.kurmakaeva.anastasia.lockquote.viewmodel.LyricPasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class LyricWebViewFragment : Fragment() {

    private lateinit var binding: FragmentLyricWebviewBinding

    private val viewModel by viewModel<LyricPasswordViewModel>()

    private val args by navArgs<LyricWebViewFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_lyric_webview, container, false)

        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        var numberOfWords = 0

        // Enable Javascript
        val webSettings = binding.lyricWebView.settings

        // webSettings.javaScriptEnabled = true
        webSettings.allowContentAccess = true
        webSettings.disabledActionModeMenuItems = MENU_ITEM_WEB_SEARCH

        binding.lyricWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return true
            }
        }

        val clipboard = activity?.getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.addPrimaryClipChangedListener {
            val regexWhiteSpace = Regex("(\\s+|\\\\n)")
            var selectedText = clipboard.primaryClip?.getItemAt(0)?.text

            if (selectedText != null) {
                val lyricSelectionTextView = view?.findViewById<TextView>(R.id.lyricSelectionTextView)
                selectedText = stripOfBracketContent(selectedText.toString())
                selectedText = removeWeirdChars(selectedText.toString()).trim()
                lyricSelectionTextView?.text = selectedText
                lyricSelectionTextView?.movementMethod = ScrollingMovementMethod()
            } else {
                showError(
                    requireContext(),
                    getString(R.string.error_something_wrong)
                )
            }

            numberOfWords = selectedText?.split(regexWhiteSpace)?.count() ?: 0

            binding.useSelectionButton.text = String.format(context?.resources?.getString(R.string.use_button) + " ($numberOfWords)")

        }

        viewModel.getSong(args.position)
        viewModel.selectedSong.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val lyricUrl = "https://www.google.com/amp/s/genius.com/amp" + it.path
            binding.lyricWebView.loadUrl(lyricUrl)
        })

        binding.useSelectionButton.text = String.format(resources.getString(R.string.use_button) + " ($numberOfWords)")

        binding.useSelectionButton.setOnClickListener {
            extractSelection(numberOfWords)
        }

        return binding.root
    }

    private fun extractSelection(numberOfWords: Int) {
        val selectTextHint = getText(R.string.select_text_hint)
        val selectedText = removeWeirdChars(
            binding.lyricSelectionTextView
                .text
                .toString())
            .trim()

        when {
            numberOfWords > 15 -> {
                context?.let {
                    showError(
                        it,
                        getString(R.string.error_too_many_words)
                    )
                }
            }
            binding.lyricSelectionTextView.text.contains(selectTextHint) -> {
                context?.let {
                    showError(
                        it,
                        getString(R.string.error_copy_not_tapped)
                    )
                }
            }
            else -> {
                viewModel.getSelectedText(selectedText)
                viewModel.selectedText.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    val passwordString = firstCharOfEveryWordOf(it).joinToString("")
                    val action = LyricWebViewFragmentDirections
                        .actionLyricWebViewFragmentToGeneratedPasswordFragment(it, passwordString)
                    this.findNavController().navigate(action)
                })
            }
        }
    }

    private fun stripOfBracketContent(value: String?): String? {
        val string = value ?: return value

        val startingBracket = string.indexOf("[")
        val closingBracket = string.indexOf("]")

        if (startingBracket == -1 || closingBracket == -1) { return value }

        return value.removeRange(startingBracket, closingBracket+1)
    }

    private fun removeWeirdChars(selectedText: String): String {
        val modified = selectedText.trim()

        return modified
            .replace("\n", " ")
            .replace("(", "")
            .replace(")", "")
            .replace("\"", "")
            .replace("'", "")
    }

    private fun firstCharOfEveryWordOf(selectedTextFromLyric: String): ArrayList<String> {
        val regex = Regex("(\\s+|\\\\n)")
        return ArrayList(
            selectedTextFromLyric
                .split(regex)
                .map {
                    if (it.isEmpty()) { return@map "" }
                    it.first().toString()
                }
                .filter { !it.isBlank() }
                .joinToString(",")
                .filter { it.isLetterOrDigit() }
                .split(",")
        )
    }
}