package com.kurmakaeva.anastasia.lockquote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kurmakaeva.anastasia.lockquote.ui.common.SearchView
import com.kurmakaeva.anastasia.lockquote.ui.theme.accent
import com.kurmakaeva.anastasia.lockquote.ui.theme.accentDark
import com.kurmakaeva.anastasia.lockquote.ui.theme.pinkGradient
import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchBoxViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalComposeUiApi
class StartFragment : Fragment() {

    private val viewModel: SearchBoxViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                StartScreen()
            }
        }
    }

    @Composable
    fun StartScreen() {
        val query = viewModel.query.collectAsState()
        val action = StartFragmentDirections.actionGoToSearchResultsFragment(query.value)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(pinkGradient)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            SearchView(
                query = query.value,
                onQueryChanged = { viewModel.onQueryChanged(it) },
                onSearchClick = { findNavController().navigate(action) },
                onClearClick = { viewModel.onClearClick() },
            )
        }
    }

    @Preview
    @Composable
    fun StartScreenPreview() {
        StartScreen()
    }
}
