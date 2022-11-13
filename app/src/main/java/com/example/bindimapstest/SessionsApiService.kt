package com.example.bindimapstest

import com.example.bindimaps.SessionsItem
import com.example.bindimaps.VenueItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL_SESSION =
    "https://tinyurl.com/mr8nu9ue/"

private const val BASE_URL_VENUE =
    "https://tinyurl.com/2xwsu5y5/"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofitSession = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_SESSION)
    .build()

private val retrofitVenue = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_VENUE)
    .build()

/**
 * A public interface that exposes the [getPhotos] method
 */
interface SessionsApiService {
    /**
     * Returns a [List] of [MarsPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET(".")
    suspend fun getSessions() : List<SessionsItem>

    @GET(".")
    suspend fun getVenues() : List<VenueItem>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object SessionsApi {
    val retrofitServiceSession: SessionsApiService by lazy { retrofitSession.create(SessionsApiService::class.java) }
    val retrofitServiceVenue: SessionsApiService by lazy { retrofitVenue.create(SessionsApiService::class.java) }
}