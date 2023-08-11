package com.kurmakaeva.anastasia.lockquote.model

import com.squareup.moshi.Json

data class GeniusSearchResponse(
    val response: GeniusResponse
)

data class GeniusResponse(
    val hits: List<GeniusHit>
)

data class GeniusHit(
    val result: GeniusHitResult
)

data class GeniusHitResult(
    @Json(name = "id") val id: Long,
    @Json(name = "api_path") val apiPath: String,
    @Json(name = "path") val path: String,
    @Json(name = "title") val title: String,
    @Json(name = "header_image_thumbnail_url") val imageThumbnailUrl: String,
    @Json(name = "primary_artist") val primaryArtist: GeniusPrimaryArtist
)

data class GeniusPrimaryArtist(
    val name: String
)