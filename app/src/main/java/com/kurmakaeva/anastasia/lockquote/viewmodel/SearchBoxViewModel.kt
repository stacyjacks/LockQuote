package com.kurmakaeva.anastasia.lockquote.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SearchBoxViewModel: ViewModel() {
    val query = MutableStateFlow("")

    fun onQueryChanged(newQuery: String) {
        query.value = newQuery
    }

    fun onClearClick() {
        query.value = ""
    }
}