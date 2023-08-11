package com.kurmakaeva.anastasia.lockquote.repository

import com.kurmakaeva.anastasia.lockquote.domain.InterfaceGeniusRepo
import com.kurmakaeva.anastasia.lockquote.model.GeniusHit
import com.kurmakaeva.anastasia.lockquote.model.SongSummaryViewData
import com.kurmakaeva.anastasia.lockquote.service.*

class GeniusRepo(private val geniusSearchService: GeniusSearchService) : InterfaceGeniusRepo {
    private var cachedSongs: MutableList<GeniusHit> = mutableListOf()

    override suspend fun searchByTerm(term: String): List<SongSummaryViewData> {
        val songSearchCall = geniusSearchService.searchSongByTerm(term)
        val listOfSongResults = songSearchCall.response
        cachedSongs = listOfSongResults.hits.toMutableList()

        return listOfSongResults.hits.map {
            SongSummaryViewData(
                id = it.result.id,
                name = it.result.primaryArtist.name,
                apiPath = it.result.apiPath,
                path = it.result.path,
                title = it.result.title,
                imgThumbnailUrl = it.result.imageThumbnailUrl
            )
        }
    }

    override suspend fun getSong(index: Int): SongSummaryViewData {
        return cachedSongs[index].result.let {
            SongSummaryViewData(
                it.id,
                it.apiPath,
                it.path,
                it.title,
                it.imageThumbnailUrl,
                it.primaryArtist.name
            )
        }
    }
}