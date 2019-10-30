package com.example.android.lockquote.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

private const val BASE_URL = "https://api.genius.com"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface GeniusSearchService {
    @GET("/search")

    fun searchSongByTerm(@Query("q") q: String): Call<GeniusSearchResponse>

    companion object {
        val instance: GeniusSearchService by lazy {

            val token = "fUXHqO4oK-lEh7wssQdKAskOe6m5SBv_K2wGvFg25FWowsEfEPgxSgsxl1RbXH2y"

            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(newRequest)
            }.build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .client(client)
                .build()

            retrofit.create<GeniusSearchService>(GeniusSearchService::class.java)
        }
    }
}