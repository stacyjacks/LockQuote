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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kurmakaeva.anastasia.lockquote.BuildConfig
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.ui.common.PasswordView
import com.kurmakaeva.anastasia.lockquote.ui.common.RegularButton
import com.kurmakaeva.anastasia.lockquote.ui.theme.extraLargeText
import com.kurmakaeva.anastasia.lockquote.ui.theme.mediumText
import com.kurmakaeva.anastasia.lockquote.ui.theme.pinkGradient
import com.kurmakaeva.anastasia.lockquote.ui.theme.white
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameResultFragment: Fragment() {

    private val args by navArgs<GameResultFragmentArgs>()
    private val versionName = BuildConfig.VERSION_NAME

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                GameResultsScreen(args.passwordString)
            }
        }
    }

    @Composable
    private fun GameResultsScreen(pwd: String) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.vinylrecordblack)
        )

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(pinkGradient)
        ) {
            val width = this.maxWidth

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.well_done),
                    modifier = Modifier.padding(12.dp),
                    color = white,
                    textAlign = TextAlign.Center,
                    style = extraLargeText
                )

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                        .background(
                            color = white,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.congrats_new_password),
                        modifier = Modifier.padding(20.dp),
                        style = mediumText
                    )

                    PasswordView(text = pwd)

                    RegularButton(
                        onClick = { copyToClipboard(pwd = pwd) },
                        modifier = Modifier
                            .padding(20.dp)
                            .width(200.dp),
                        buttonText = stringResource(id = R.string.copy_button)
                    )

                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier.size(250.dp),
                        speed = 1.25f,
                        iterations = LottieConstants.IterateForever
                    )

                    Row {
                        RegularButton(
                            onClick = { onRetryClicked() },
                            modifier = Modifier
                                .padding(4.dp)
                                .width(width.div(2)),
                            buttonText = stringResource(id = R.string.another_go)
                        )
                        RegularButton(
                            onClick = { onNewPasswordClicked() },
                            modifier = Modifier
                                .padding(4.dp)
                                .width(width.div(2)),
                            buttonText = stringResource(id = R.string.make_new_button)
                        )
                    }

                    Row(
                        modifier = Modifier.padding(top = 50.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.about_title)
                                .replace("%s", versionName)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_lightbulb),
                            contentDescription = stringResource(id = R.string.lightbulb_cd),
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(30.dp)
                                .clickable {
                                    showInfo()
                                }
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun GameResultsPreview() {
        GameResultsScreen("O77u")
    }

    private fun showInfo() {
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
    
    private fun copyToClipboard(pwd: String) {
        val clipboard: ClipboardManager? =
            activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("passwordReady", pwd)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(
            requireActivity(), getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT
        ).show()
    }

    private fun onRetryClicked() {
        findNavController().popBackStack(R.id.gameTaskOneFragment, false)
    }

    private fun onNewPasswordClicked() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}