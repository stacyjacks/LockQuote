package com.kurmakaeva.anastasia.lockquote.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GeneratedPasswordViewModel : ViewModel() {
    private val _passwordString = MutableLiveData<String>()
    val passwordString: LiveData<String>
        get() = _passwordString

    fun getPasswordStringFromSelectedText(passwordStringFromLyric: String) {
        _passwordString.value = passwordStringFromLyric
    }
}
