package com.kurmakaeva.anastasia.lockquote.ui

import android.app.AlertDialog
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentLyricWebviewBinding
import com.kurmakaeva.anastasia.lockquote.viewmodel.LyricPasswordViewModel
import com.kurmakaeva.anastasia.lockquote.viewmodel.LyricPasswordViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class LyricWebViewFragment : Fragment() {

    private lateinit var binding: FragmentLyricWebviewBinding

    private val viewModel: LyricPasswordViewModel by viewModels()

    private val args by navArgs<LyricWebViewFragmentArgs>()

    private val regexWhiteSpace = Regex("(\\s+|\\\\n)")

    private var numberOfWords = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_lyric_webview, container, false)

        // Enable Javascript
        val webSettings = binding.lyricWebView.settings

        webSettings.allowContentAccess = true
        webSettings.disabledActionModeMenuItems = MENU_ITEM_WEB_SEARCH
        webSettings.javaScriptEnabled = true

        binding.lyricWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return true
            }
        }

        val clipboard = activity?.getSystemService(CLIPBOARD_SERVICE) as? ClipboardManager
        clipboard?.addPrimaryClipChangedListener {
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSong(args.position)
                viewModel.selectedSong.collect {
                    when (it) {
                        LyricPasswordViewState.Error -> {
                            // handle error
                        }
                        LyricPasswordViewState.Loading -> {
                            // handle loading
                        }
                        is LyricPasswordViewState.Success -> {
                            val lyricUrl = "https://genius.com" + it.song.path
                            binding.lyricWebView.loadUrl(lyricUrl)
                        }
                    }
                }
            }
        }

        binding.useSelectionButton.text = String.format(resources.getString(R.string.use_button) + " ($numberOfWords)")

        binding.useSelectionButton.setOnClickListener {
            extractSelection(numberOfWords)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.selectedText.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.lyricSelectionTextView.text = it
            numberOfWords = it?.split(regexWhiteSpace)?.count() ?: 0
        })
    }

    private fun extractSelection(numberOfWords: Int) {
        val selectTextHint = getText(R.string.select_text_hint)
        val selectedText = removeWeirdChars(
            binding.lyricSelectionTextView
                .text
                .toString())
            .trim()

        when {
            numberOfWords > 15 || numberOfWords < 3 -> {
                context?.let {
                    showError(
                        it,
                        getString(R.string.error_too_many_too_little_words)
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

    private fun showError(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.ok_button), null)
            .create()
            .show()
    }
}