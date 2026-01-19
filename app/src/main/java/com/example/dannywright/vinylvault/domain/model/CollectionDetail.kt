package com.example.dannywright.vinylvault.domain.model

data class CollectionRelease(
    val id: Int,
    val title: String,
    val artist: String,
    val year: String?,
    val coverImageUrl: String?,
    val genres: List<String>,
    val format: String?,
    val addedDate: Long
)