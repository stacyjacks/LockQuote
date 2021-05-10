package com.kurmakaeva.anastasia.lockquote.service

import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


interface GeniusSearchService {
    @GET("/search")

    suspend fun searchSongByTerm(@Query("q") q: String): GeniusSearchResponse
}