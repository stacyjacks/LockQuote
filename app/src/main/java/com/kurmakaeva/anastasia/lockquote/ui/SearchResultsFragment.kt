package com.kurmakaeva.anastasia.lockquote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kurmakaeva.anastasia.lockquote.R
import com.kurmakaeva.anastasia.lockquote.model.SongSummaryViewData
import com.kurmakaeva.anastasia.lockquote.ui.common.ErrorScreen
import com.kurmakaeva.anastasia.lockquote.ui.common.SearchView
import com.kurmakaeva.anastasia.lockquote.ui.common.SkeletonListItem
import com.kurmakaeva.anastasia.lockquote.ui.theme.accent
import com.kurmakaeva.anastasia.lockquote.ui.theme.lightGrey
import com.kurmakaeva.anastasia.lockquote.ui.theme.white
import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchBoxViewModel
import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchViewModel
import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchViewState
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalComposeUiApi
class SearchResultsFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private val searchBoxViewModel: SearchBoxViewModel by viewModels()

    private val args by navArgs<SearchResultsFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier
                    .background(color = accent)
                ) {
                    val query by searchBoxViewModel.query.collectAsState()

                    SearchView(
                        query = query,
                        onQueryChanged = { searchBoxViewModel.onQueryChanged(it) },
                        onClearClick = { searchBoxViewModel.onClearClick() },
                        onSearchClick = { viewModel.refresh(it) }
                    )

                    Surface(modifier = Modifier.background(color = lightGrey)) {
                        viewModel.refresh(args.query)

                        val state by viewModel.searchResults.collectAsState()

                        LazyColumn(modifier = Modifier.background(color = lightGrey)) {
                            when (state) {
                                SearchViewState.Loading -> {
                                    items(count = 8) {
                                        SkeletonListItem()
                                    }
                                }
                                SearchViewState.Error -> {
                                    item {
                                        ErrorScreen()
                                    }
                                }
                                is SearchViewState.Success -> {
                                    items((state as SearchViewState.Success).listOfSongs.count()) { index ->
                                        SongCard(
                                            songData = (state as SearchViewState.Success)
                                                .listOfSongs[index]) {
                                            navigateToSearchResult(index)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateToSearchResult(position: Int) {
        val action = SearchResultsFragmentDirections
            .actionSearchResultsFragmentToLyricWebViewFragment(position)
        this.findNavController().navigate(action)
    }

    @Composable
    fun SongCard(songData: SongSummaryViewData, onClick: () -> Unit) {
        Column(
            modifier = Modifier.background(color = lightGrey)
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .height(IntrinsicSize.Min)
                    .background(
                        color = white,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideImage(
                    modifier = Modifier
                        .width(75.dp)
                        .height(75.dp)
                        .padding(4.dp),
                    imageModel = songData.header_image_thumbnail_url,
                    contentScale = ContentScale.FillHeight,
                    alignment = Alignment.CenterStart,
                    placeHolder = painterResource(id = R.drawable.lockquote_bw)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = songData.title,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = songData.name,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}
