package com.example.dannywright.vinylvault.domain.model

data class ReleaseDetail(
    val id: Int,
    val title: String,
    val artists: List<String>,
    val year: Int?,
    val genres: List<String>,
    val styles: List<String>,
    val coverImageUrl: String?,
    val tracklist: List<Track>,
    val labels: List<String>,
    val country: String?,
    val released: String?,
    val notes: String?,
    val format: String?
)

data class Track(
    val position: String,
    val title: String,
    val duration: String?
)