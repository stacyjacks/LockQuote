package com.kurmakaeva.anastasia.lockquote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kurmakaeva.anastasia.lockquote.R
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
        val query = viewModel.query.value
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.colorAccent)),
            verticalArrangement = Arrangement.Center
        ) {
            SearchView(
                query = query,
                onQueryChanged = { viewModel.onQueryChanged(it) },
                onSearchClick = { },
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
