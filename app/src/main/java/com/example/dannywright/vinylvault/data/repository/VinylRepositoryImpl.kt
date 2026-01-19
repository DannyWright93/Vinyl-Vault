package com.example.dannywright.vinylvault.data.repository

import com.example.dannywright.vinylvault.data.local.dao.ReleaseDao
import com.example.dannywright.vinylvault.data.remote.DiscogsApiService
import com.example.dannywright.vinylvault.domain.model.CollectionRelease
import com.example.dannywright.vinylvault.domain.model.Release
import com.example.dannywright.vinylvault.domain.model.ReleaseDetail
import com.example.dannywright.vinylvault.domain.repository.VinylRepository
import com.example.dannywright.vinylvault.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VinylRepositoryImpl @Inject constructor(
    private val api: DiscogsApiService,
    private val dao: ReleaseDao
) : VinylRepository {

    override suspend fun searchReleases(query: String): Resource<List<Release>> {
        return try {
            val response = api.searchReleases(query)
            val releases = response.results.map { it.toRelease() }
            Resource.Success(releases)
        } catch (e: Exception) {
            Resource.Error(
                message = e.localizedMessage ?: "An unexpected error occurred"
            )
        }
    }

    override suspend fun getReleaseDetail(releaseId: Int): Resource<ReleaseDetail> {
        return try {
            val response = api.getReleaseDetails(releaseId)
            val releaseDetail = response.toReleaseDetail()
            Resource.Success(releaseDetail)
        } catch (e: Exception) {
            Resource.Error(
                message = e.localizedMessage ?: "An unexpected error occurred"
            )
        }
    }

    override fun getCollection(): Flow<List<CollectionRelease>> {
        return dao.getAllReleases().map { entities ->
            entities.map { it.toCollectionRelease() }
        }
    }

    override suspend fun addToCollection(release: Release): Resource<Unit> {
        return try {
            dao.insertRelease(release.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(
                message = e.localizedMessage ?: "Failed to add to collection"
            )
        }
    }

    override suspend fun removeFromCollection(releaseId: Int): Resource<Unit> {
        return try {
            dao.deleteReleaseById(releaseId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(
                message = e.localizedMessage ?: "Failed to remove from collection"
            )
        }
    }

    override suspend fun isInCollection(releaseId: Int): Boolean {
        return dao.isReleaseInCollection(releaseId)
    }
}