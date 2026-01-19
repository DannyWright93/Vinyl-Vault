package com.example.dannywright.vinylvault.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dannywright.vinylvault.data.local.Converters

@Entity(tableName = "releases")
@TypeConverters(Converters::class)
data class ReleaseEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val artist: String,
    val year: String?,
    val coverImageUrl: String?,
    val genres: List<String>,
    val format: String?,
    val addedDate: Long
)