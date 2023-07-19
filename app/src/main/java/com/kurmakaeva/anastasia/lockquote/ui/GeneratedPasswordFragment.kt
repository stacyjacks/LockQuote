package com.kurmakaeva.anastasia.lockquote.ui

import android.os.Bundle
import android.text.SpannedString
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.databinding.FragmentGeneratedPasswordBinding
import com.kurmakaeva.anastasia.lockquote.ui.common.RegularButton
import com.kurmakaeva.anastasia.lockquote.ui.theme.accent
import com.kurmakaeva.anastasia.lockquote.ui.theme.largeText
import com.kurmakaeva.anastasia.lockquote.ui.theme.lightGrey
import com.kurmakaeva.anastasia.lockquote.ui.theme.mediumText
import com.kurmakaeva.anastasia.lockquote.ui.theme.pinkGradient
import com.kurmakaeva.anastasia.lockquote.ui.theme.smallTextItalics
import com.kurmakaeva.anastasia.lockquote.ui.theme.white
import com.kurmakaeva.anastasia.lockquote.viewmodel.GeneratedPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneratedPasswordFragment : Fragment() {

    private val viewModel: GeneratedPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(pinkGradient)
                ) {
                    //GeneratedPasswordLoadingScreen()
                    GeneratedPasswordMainScreen()
                }
            }
        }
    }

    @Composable
    private fun GeneratedPasswordMainScreen() {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxHeight()
                .background(color = white, shape = RoundedCornerShape(24.dp)),
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
                    text = "One two three uh!",
                    modifier = Modifier.padding(12.dp),
                    style = smallTextItalics
                )
            }

            Row(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) {
                Text(text = "ottu", style = largeText)
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
                        text = "4",
                        style = largeText,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Text(
                        text = "characters",
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
                onClick = {},
                modifier = Modifier.width(230.dp),
                buttonText = stringResource(id = R.string.next_level_button)
            )

            RegularButton(
                onClick = {},
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(300.dp)
            )

            Text(
                text = stringResource(id = R.string.just_a_sec).uppercase(),
                color = white,
                textAlign = TextAlign.Center,
                style = largeText
            )
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFD81B60)
    private fun PreviewGenerated() {
        GeneratedPasswordMainScreen()
        //GeneratedPasswordLoadingScreen()
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val args by navArgs<GeneratedPasswordFragmentArgs>()
//
//        viewModel.getPasswordStringFromSelectedText(args.passwordString)
//        viewModel.passwordString.observe(viewLifecycleOwner, Observer {
//            val modPasswordString = charReplacer(it)
//            binding.generatedPass.text = modPasswordString
//
//            binding.numberOfCharacters.text = numberOfCharCalculator(it)
//
//            binding.helpMeRemember.setOnClickListener {
//                val action = GeneratedPasswordFragmentDirections
//                    .actionGeneratedPasswordFragmentToActivityGame(modPasswordString, args.selectedText)
//                this.findNavController().navigate(action)
//            }
//        })
//
//        binding.tryAgainButton.setOnClickListener {
//            this.findNavController().navigateUp()
//        }
//
//        binding.selectedTextFromLyric.text = makeFirstLetterBold(args.selectedText)
//    }

    private fun numberOfCharCalculator(passwordString: String): String {
        return passwordString.length.toString()
    }

    private fun makeFirstLetterBold(selectedLyric: String): SpannedString {
        var customString = SpannedString("")
        val regex = Regex("(\\s|\\\\n)")
        val lyricWordsArray = selectedLyric.split(regex).toTypedArray()
        for (word in lyricWordsArray) {
            if (word.isBlank()) {
                continue
            }

            val firstLetterBold = word.first()
            val normalText = word.drop(1)
            val customWord = SpannedString(buildSpannedString {
                bold {
                    append(firstLetterBold)
                }
                append(normalText)
            })
            customString = TextUtils.concat(customString, " ", customWord) as SpannedString
        }
        return customString.drop(1) as SpannedString
    }

    private fun charReplacer(passwordString: String): String {
        val replaceA = arrayListOf("A", "a", "4")
        val replaceS = arrayListOf("S", "s", "5")
        val replaceE = arrayListOf("E", "e", "3")
        val replaceT = arrayListOf("T", "t", "7")
        val replaceI = arrayListOf("I", "i", "1")
        val replaceO = arrayListOf("O", "o", "0")
        val replaceG = arrayListOf("G", "g", "6")

        return passwordString
            .replace("A", replaceA.random())
            .replace("Á", replaceA.random())
            .replace("À", replaceA.random())
            .replace("Â", replaceA.random())
            .replace("Ä", replaceA.random())
            .replace("á", replaceA.random())
            .replace("à", replaceA.random())
            .replace("â", replaceA.random())
            .replace("ä", replaceA.random())
            .replace("a", replaceA.random())
            .replace("S", replaceS.random())
            .replace("s", replaceS.random())
            .replace("E", replaceE.random())
            .replace("É", replaceE.random())
            .replace("È", replaceE.random())
            .replace("Ê", replaceE.random())
            .replace("Ë", replaceE.random())
            .replace("e", replaceE.random())
            .replace("é", replaceE.random())
            .replace("è", replaceE.random())
            .replace("ê", replaceE.random())
            .replace("ë", replaceE.random())
            .replace("T", replaceT.random())
            .replace("t", replaceT.random())
            .replace("I", replaceI.random())
            .replace("Í", replaceE.random())
            .replace("Ì", replaceE.random())
            .replace("Î", replaceE.random())
            .replace("Ï", replaceE.random())
            .replace("i", replaceI.random())
            .replace("í", replaceE.random())
            .replace("ì", replaceE.random())
            .replace("î", replaceE.random())
            .replace("ï", replaceE.random())
            .replace("O", replaceO.random())
            .replace("Ó", replaceE.random())
            .replace("Ò", replaceE.random())
            .replace("Ô", replaceE.random())
            .replace("Ö", replaceE.random())
            .replace("o", replaceO.random())
            .replace("ó", replaceE.random())
            .replace("ò", replaceE.random())
            .replace("ô", replaceE.random())
            .replace("ö", replaceE.random())
            .replace("G", replaceG.random())
            .replace("g", replaceG.random())
            .replace("Ú", "U")
            .replace("Ù", replaceE.random())
            .replace("Û", replaceE.random())
            .replace("Ü", replaceE.random())
            .replace("ú", "u")
            .replace("ù", replaceE.random())
            .replace("û", replaceE.random())
            .replace("ü", replaceE.random())
    }
}