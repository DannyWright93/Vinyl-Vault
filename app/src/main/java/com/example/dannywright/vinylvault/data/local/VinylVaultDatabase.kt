package com.example.dannywright.vinylvault.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dannywright.vinylvault.data.local.dao.ReleaseDao
import com.example.dannywright.vinylvault.data.local.entity.ReleaseEntity

@Database(
    entities = [ReleaseEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class VinylVaultDatabase : RoomDatabase() {
    abstract fun releaseDao(): ReleaseDao
}