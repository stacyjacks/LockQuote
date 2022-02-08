package com.kurmakaeva.anastasia.lockquote.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.kurmakaeva.anastasia.lockquote.App
import com.kurmakaeva.anastasia.lockquote.BuildConfig
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentGameResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameResultFragment: Fragment() {

    private lateinit var binding: FragmentGameResultBinding
    private val args by navArgs<GameResultFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game_result,
            container,
            false)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_item) {
            showInfo()
        }

        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val password = binding.passwordFinal
        password.text = args.passwordString
        password.setTextIsSelectable(true)

        binding.vinyl.speed = 1.25f

        binding.copyButton.setOnClickListener {
            val clipboard: ClipboardManager? =
                activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("passwordReady", password.text)
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(requireActivity(), getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
        }

        binding.anotherGoButton.setOnClickListener {
            activity?.finish()
        }

        binding.makeNewPasswordButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun showInfo() {
        val versionName = BuildConfig.VERSION_NAME
        val dialogTitle = getString(
            R.string.about_title,
            versionName
        )
        val dialogMessage = getString(R.string.about_message)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }
}