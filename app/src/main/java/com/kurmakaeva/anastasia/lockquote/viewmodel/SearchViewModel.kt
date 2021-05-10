package com.kurmakaeva.anastasia.lockquote.viewmodel

import androidx.lifecycle.*
import com.kurmakaeva.anastasia.lockquote.repository.InterfaceGeniusRepo
import kotlinx.coroutines.launch


class SearchViewModel(private val repo: InterfaceGeniusRepo): ViewModel() {

    data class SongSummaryViewData(
        val id: Long = 0,
        val api_path: String = "",
        val path: String = "",
        val title: String = "",
        val header_image_thumbnail_url: String = "",
        val name: String = ""
    )

    private val _searchResults = MutableLiveData<List<SongSummaryViewData>>()
    val searchResults: LiveData<List<SongSummaryViewData>>
        get() = _searchResults

    private val _selectedSong = MutableLiveData<SongSummaryViewData>()
    val selectedSong: LiveData<SongSummaryViewData>
        get() = _selectedSong

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

    fun getSong(position: Int) {
        viewModelScope.launch {
            try {
                _selectedSong.value = repo.getSong(position)
            } catch (e: java.lang.Exception) {
                showSnackbarEvent()
            }
        }
    }

    private fun showSnackbarEvent() {
        _showSnackbar.value = true
    }
}
