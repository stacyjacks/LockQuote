package com.kurmakaeva.anastasia.lockquote.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchBoxViewModel: ViewModel() {
    val query = mutableStateOf("")

    fun onQueryChanged(newQuery: String) {
        query.value = newQuery
    }

    fun onClearClick() {
        query.value = ""
    }
}