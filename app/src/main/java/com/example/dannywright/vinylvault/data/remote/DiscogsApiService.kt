package com.example.dannywright.vinylvault.data.remote

import com.example.dannywright.vinylvault.data.remote.dto.DiscogsReleaseDetailDto
import com.example.dannywright.vinylvault.data.remote.dto.DiscogsSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscogsApiService {

    @GET("database/search")
    suspend fun searchReleases(
        @Query("q") query: String,
        @Query("type") type: String = "release",
        @Query("per_page") perPage: Int = 50
    ): DiscogsSearchResponse

    @GET("releases/{release_id}")
    suspend fun getReleaseDetails(
        @Path("release_id") releaseId: Int
    ): DiscogsReleaseDetailDto

    companion object {
        const val BASE_URL = "https://api.discogs.com/"
    }
}