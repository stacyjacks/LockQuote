package com.kurmakaeva.anastasia.lockquote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurmakaeva.anastasia.lockquote.model.SongSummaryViewData
import com.kurmakaeva.anastasia.lockquote.repository.InterfaceGeniusRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LyricPasswordViewModel @Inject constructor(private val repo: InterfaceGeniusRepo): ViewModel() {

    private val _selectedSong = MutableLiveData<SongSummaryViewData>()
    val selectedSong: LiveData<SongSummaryViewData>
        get() = _selectedSong

    private val _selectedText = MutableLiveData<String>()
    val selectedText: LiveData<String>
        get() = _selectedText

    private val _passwordString = MutableLiveData<String>()
    val passwordString: LiveData<String>
        get() = _passwordString

    fun getSong(position: Int) {
        viewModelScope.launch {
            try {
                _selectedSong.value = repo.getSong(position)
            } catch (e: Exception) {

            }
        }
    }

    fun getSelectedText(selectedTextFromLyric: String) {
        _selectedText.value = selectedTextFromLyric
    }

    fun getPasswordStringFromSelectedText(passwordStringFromLyric: String) {
        _passwordString.value = passwordStringFromLyric
    }
}