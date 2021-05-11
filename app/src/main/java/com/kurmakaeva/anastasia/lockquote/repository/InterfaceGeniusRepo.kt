package com.kurmakaeva.anastasia.lockquote.repository

import com.kurmakaeva.anastasia.lockquote.model.SongSummaryViewData

interface InterfaceGeniusRepo {
    suspend fun searchByTerm(term: String): List<SongSummaryViewData>
    suspend fun getSong(index: Int): SongSummaryViewData
}