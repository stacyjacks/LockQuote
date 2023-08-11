package com.kurmakaeva.anastasia.lockquote.viewmodel

import android.util.Log
import com.kurmakaeva.anastasia.lockquote.model.SongSummaryViewData
import com.kurmakaeva.anastasia.lockquote.domain.InterfaceGeniusRepo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: InterfaceGeniusRepo): ViewModel() {

    private val _searchResults = MutableStateFlow<SearchViewState>(SearchViewState.Loading)
    val searchResults: StateFlow<SearchViewState> = _searchResults

    fun refresh(query: String) {
        getSearchResults(query)
    }

    private fun getSearchResults(query: String) {
        _searchResults.value = SearchViewState.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                repo.searchByTerm(query)
            }.onSuccess { results ->
                _searchResults.value = SearchViewState.Success(results)
            }.onFailure { e ->
                _searchResults.value = SearchViewState.Error
                // to do replace with better internal error handling
                Log.i("Search request failed", e.localizedMessage ?: "Error unknown")
            }
        }
    }
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    object Error : SearchViewState()
    data class Success(val listOfSongs: List<SongSummaryViewData>): SearchViewState()
}
