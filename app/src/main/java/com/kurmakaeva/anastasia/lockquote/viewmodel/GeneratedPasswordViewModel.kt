package com.kurmakaeva.anastasia.lockquote.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GeneratedPasswordViewModel : ViewModel() {

    private val _passwordString = MutableStateFlow("")
    val passwordString: StateFlow<String>
        get() = _passwordString

    private val _showLoading = MutableStateFlow(true)
    val showLoading: StateFlow<Boolean>
        get() = _showLoading

    fun getModPasswordFromSelectedText(passwordStringFromLyric: String) {
        _passwordString.value = charReplacer(passwordStringFromLyric)
    }

    fun hideLoading() {
        _showLoading.value = false
    }

    private fun charReplacer(passwordString: String): String {
        val replaceA = arrayListOf("A", "a", "4")
        val replaceS = arrayListOf("S", "s", "5")
        val replaceE = arrayListOf("E", "e", "3")
        val replaceT = arrayListOf("T", "t", "7")
        val replaceI = arrayListOf("I", "i", "1")
        val replaceO = arrayListOf("O", "o", "0")
        val replaceG = arrayListOf("G", "g", "6")

        return passwordString
            .replace("A", replaceA.random())
            .replace("Á", replaceA.random())
            .replace("À", replaceA.random())
            .replace("Â", replaceA.random())
            .replace("Ä", replaceA.random())
            .replace("á", replaceA.random())
            .replace("à", replaceA.random())
            .replace("â", replaceA.random())
            .replace("ä", replaceA.random())
            .replace("a", replaceA.random())
            .replace("S", replaceS.random())
            .replace("s", replaceS.random())
            .replace("E", replaceE.random())
            .replace("É", replaceE.random())
            .replace("È", replaceE.random())
            .replace("Ê", replaceE.random())
            .replace("Ë", replaceE.random())
            .replace("e", replaceE.random())
            .replace("é", replaceE.random())
            .replace("è", replaceE.random())
            .replace("ê", replaceE.random())
            .replace("ë", replaceE.random())
            .replace("T", replaceT.random())
            .replace("t", replaceT.random())
            .replace("I", replaceI.random())
            .replace("Í", replaceE.random())
            .replace("Ì", replaceE.random())
            .replace("Î", replaceE.random())
            .replace("Ï", replaceE.random())
            .replace("i", replaceI.random())
            .replace("í", replaceE.random())
            .replace("ì", replaceE.random())
            .replace("î", replaceE.random())
            .replace("ï", replaceE.random())
            .replace("O", replaceO.random())
            .replace("Ó", replaceE.random())
            .replace("Ò", replaceE.random())
            .replace("Ô", replaceE.random())
            .replace("Ö", replaceE.random())
            .replace("o", replaceO.random())
            .replace("ó", replaceE.random())
            .replace("ò", replaceE.random())
            .replace("ô", replaceE.random())
            .replace("ö", replaceE.random())
            .replace("G", replaceG.random())
            .replace("g", replaceG.random())
            .replace("Ú", "U")
            .replace("Ù", replaceE.random())
            .replace("Û", replaceE.random())
            .replace("Ü", replaceE.random())
            .replace("ú", "u")
            .replace("ù", replaceE.random())
            .replace("û", replaceE.random())
            .replace("ü", replaceE.random())
    }
}
