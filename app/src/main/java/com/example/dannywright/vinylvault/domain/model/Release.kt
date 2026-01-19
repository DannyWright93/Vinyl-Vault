package com.example.dannywright.vinylvault.domain.model

data class Release(
    val id: Int,
    val title: String,
    val artist: String,
    val year: String?,
    val coverImageUrl: String?,
    val genres: List<String>,
    val styles: List<String>,
    val format: String?,
    val country: String?,
    val label: String?
)