package com.kurmakaeva.anastasia.lockquote.repository

import com.kurmakaeva.anastasia.lockquote.model.GeniusHit
import com.kurmakaeva.anastasia.lockquote.model.SongSummaryViewData
import com.kurmakaeva.anastasia.lockquote.service.*

class GeniusRepo(private val geniusSearchService: GeniusSearchService) : InterfaceGeniusRepo {
    private var cachedSongs: MutableList<GeniusHit> = mutableListOf()

    override suspend fun searchByTerm(term: String): List<SongSummaryViewData> {
        val songSearchCall = geniusSearchService.searchSongByTerm(term)
        val listOfSongResults = songSearchCall.response
        cachedSongs.plusAssign(listOfSongResults.hits)

        return listOfSongResults.hits.map {
            SongSummaryViewData(
                id = it.result.id,
                name = it.result.primary_artist.name,
                api_path = it.result.api_path,
                path = it.result.path,
                title = it.result.title,
                header_image_thumbnail_url = it.result.header_image_thumbnail_url
            )
        }
    }

    override suspend fun getSong(index: Int): SongSummaryViewData {
        return cachedSongs.get(index).result.let {
            SongSummaryViewData(
                it.id,
                it.api_path,
                it.path,
                it.title,
                it.header_image_thumbnail_url,
                it.primary_artist.name
            )
        }
    }
}