package com.example.dannywright.vinylvault.domain.repository

import com.example.dannywright.vinylvault.domain.model.CollectionRelease
import com.example.dannywright.vinylvault.domain.model.Release
import com.example.dannywright.vinylvault.domain.model.ReleaseDetail
import com.example.dannywright.vinylvault.util.Resource
import kotlinx.coroutines.flow.Flow

interface VinylRepository {

    suspend fun searchReleases(query: String): Resource<List<Release>>

    suspend fun getReleaseDetail(releaseId: Int): Resource<ReleaseDetail>

    fun getCollection(): Flow<List<CollectionRelease>>

    suspend fun addToCollection(release: Release): Resource<Unit>

    suspend fun removeFromCollection(releaseId: Int): Resource<Unit>

    suspend fun isInCollection(releaseId: Int): Boolean
}