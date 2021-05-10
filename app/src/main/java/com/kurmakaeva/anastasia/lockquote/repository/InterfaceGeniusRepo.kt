package com.kurmakaeva.anastasia.lockquote.repository

import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchViewModel

interface InterfaceGeniusRepo {
    suspend fun searchByTerm(term: String): List<SearchViewModel.SongSummaryViewData>
    suspend fun getSong(index: Int): SearchViewModel.SongSummaryViewData
}