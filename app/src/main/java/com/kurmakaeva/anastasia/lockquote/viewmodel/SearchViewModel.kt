package com.kurmakaeva.anastasia.lockquote.viewmodel

import androidx.lifecycle.*
import com.kurmakaeva.anastasia.lockquote.model.SongSummaryViewData
import com.kurmakaeva.anastasia.lockquote.repository.InterfaceGeniusRepo
import kotlinx.coroutines.launch


class SearchViewModel(private val repo: InterfaceGeniusRepo): ViewModel() {

    private val _searchResults = MutableLiveData<List<SongSummaryViewData>>()
    val searchResults: LiveData<List<SongSummaryViewData>>
        get() = _searchResults

    private val _showSnackbar = MutableLiveData<Boolean>()
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbar

    fun getSearchResults(query: String) {
        viewModelScope.launch {
            try {
                _searchResults.value = repo.searchByTerm(query)

            } catch (e: Exception) {
                showSnackbarEvent()
            }
        }
    }

    private fun showSnackbarEvent() {
        _showSnackbar.value = true
    }
}
