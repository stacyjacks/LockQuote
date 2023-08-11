package com.kurmakaeva.anastasia.lockquote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.ui.common.PasswordView
import com.kurmakaeva.anastasia.lockquote.ui.common.RegularButton
import com.kurmakaeva.anastasia.lockquote.ui.common.makeFirstLetterBold
import com.kurmakaeva.anastasia.lockquote.ui.theme.largeText
import com.kurmakaeva.anastasia.lockquote.ui.theme.lightGrey
import com.kurmakaeva.anastasia.lockquote.ui.theme.mediumText
import com.kurmakaeva.anastasia.lockquote.ui.theme.pinkGradient
import com.kurmakaeva.anastasia.lockquote.ui.theme.smallTextItalics
import com.kurmakaeva.anastasia.lockquote.ui.theme.white
import com.kurmakaeva.anastasia.lockquote.viewmodel.GeneratedPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class GeneratedPasswordFragment : Fragment() {

    private val viewModel: GeneratedPasswordViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args by navArgs<GeneratedPasswordFragmentArgs>()
        viewModel.getModPasswordFromSelectedText(args.passwordString)

        return ComposeView(requireContext()).apply {
            setContent {
                val modPassword = viewModel.passwordString.collectAsState()
                val showLoading = viewModel.showLoading.collectAsState()

                LaunchedEffect(key1 = Unit) {
                    delay(2000L)
                    viewModel.hideLoading()
                }
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(pinkGradient)
                ) {
                    if (showLoading.value) {
                        GeneratedPasswordLoadingScreen()
                    }

                    AnimatedVisibility(
                        visible = !showLoading.value,
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        GeneratedPasswordMainScreen(
                            args.selectedText,
                            modPassword.value
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun GeneratedPasswordMainScreen(selectedLyric: String, pwd: String) {
        val action = GeneratedPasswordFragmentDirections
            .actionGeneratedPasswordFragmentToActivityGame(pwd, selectedLyric)

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
                text = stringResource(id = R.string.take_a_good_look),
                modifier = Modifier.padding(20.dp),
                style = mediumText
            )

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = lightGrey,
                        shape = RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 0.dp,
                            bottomStart = 18.dp,
                            bottomEnd = 18.dp
                        )
                    )
            ) {
                Text(
                    text = makeFirstLetterBold(selectedLyric),
                    modifier = Modifier.padding(12.dp),
                    style = smallTextItalics
                )
            }

            Row(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) {
                PasswordView(text = pwd)
            }

            Icon(
                painter = painterResource(id = R.drawable.arrow_up_24),
                contentDescription = null,
                tint = lightGrey,
            )

            Row(modifier = Modifier
                .padding(bottom = 8.dp)
                .background(
                    color = lightGrey,
                    shape = RoundedCornerShape(
                        topStart = 18.dp,
                        topEnd = 18.dp,
                        bottomStart = 18.dp,
                        bottomEnd = 18.dp
                    )
                )
                .padding(horizontal = 20.dp, vertical = 32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = pwd.length.toString(),
                        style = largeText,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.characters),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.cerebro),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(60.dp)
                )
            }

            RegularButton(
                onClick = { findNavController().navigate(action) },
                modifier = Modifier.width(230.dp),
                buttonText = stringResource(id = R.string.next_level_button)
            )

            RegularButton(
                onClick = { findNavController().navigateUp() },
                modifier = Modifier.width(230.dp),
                buttonText = stringResource(id = R.string.try_again_button)
            )
        }
    }

    @Composable
    private fun GeneratedPasswordLoadingScreen() {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.loadinglock)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .padding(top = 48.dp)
                    .size(300.dp)
            )

            Text(
                text = stringResource(id = R.string.just_a_sec).uppercase(),
                modifier = Modifier.padding(top = 16.dp),
                color = white,
                textAlign = TextAlign.Center,
                style = largeText
            )
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFD81B60)
    private fun PreviewGenerated() {
        GeneratedPasswordMainScreen("One, two, three, uh!","077u")
        //GeneratedPasswordLoadingScreen()
    }
}