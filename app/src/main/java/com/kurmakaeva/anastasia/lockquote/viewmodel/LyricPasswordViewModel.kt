package com.kurmakaeva.anastasia.lockquote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurmakaeva.anastasia.lockquote.model.SongSummaryViewData
import com.kurmakaeva.anastasia.lockquote.repository.InterfaceGeniusRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LyricPasswordViewModel @Inject constructor(private val repo: InterfaceGeniusRepo): ViewModel() {

    private val _selectedSong = MutableStateFlow<LyricPasswordViewState>(LyricPasswordViewState.Loading)
    val selectedSong: StateFlow<LyricPasswordViewState> = _selectedSong

    private val _selectedText = MutableLiveData<String>()
    val selectedText: LiveData<String>
        get() = _selectedText

    fun getSong(position: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                repo.getSong(position)
            }.onSuccess { song ->
                _selectedSong.value = LyricPasswordViewState.Success(song)
            }.onFailure {
                _selectedSong.value = LyricPasswordViewState.Error
            }
        }
    }

    fun getSelectedText(selectedTextFromLyric: String) {
        _selectedText.value = selectedTextFromLyric
    }
}

sealed class LyricPasswordViewState {
    object Loading : LyricPasswordViewState()
    object Error : LyricPasswordViewState()
    data class Success(val song: SongSummaryViewData): LyricPasswordViewState()
}