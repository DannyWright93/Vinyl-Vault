package com.example.dannywright.vinylvault.data.local.dao

import androidx.room.*
import com.example.dannywright.vinylvault.data.local.entity.ReleaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReleaseDao {

    @Query("SELECT * FROM releases ORDER BY addedDate DESC")
    fun getAllReleases(): Flow<List<ReleaseEntity>>

    @Query("SELECT * FROM releases WHERE id = :releaseId")
    suspend fun getReleaseById(releaseId: Int): ReleaseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelease(release: ReleaseEntity)

    @Delete
    suspend fun deleteRelease(release: ReleaseEntity)

    @Query("DELETE FROM releases WHERE id = :releaseId")
    suspend fun deleteReleaseById(releaseId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM releases WHERE id = :releaseId)")
    suspend fun isReleaseInCollection(releaseId: Int): Boolean
}