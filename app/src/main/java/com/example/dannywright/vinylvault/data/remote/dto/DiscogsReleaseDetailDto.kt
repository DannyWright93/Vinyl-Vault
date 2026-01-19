package com.example.dannywright.vinylvault.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DiscogsReleaseDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("artists")
    val artists: List<ArtistDto>?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("styles")
    val styles: List<String>?,
    @SerializedName("images")
    val images: List<ImageDto>?,
    @SerializedName("tracklist")
    val tracklist: List<TrackDto>?,
    @SerializedName("labels")
    val labels: List<LabelDto>?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("released")
    val released: String?,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("formats")
    val formats: List<FormatDto>?
)

data class ArtistDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int?
)

data class ImageDto(
    @SerializedName("uri")
    val uri: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int?
)

data class TrackDto(
    @SerializedName("position")
    val position: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("duration")
    val duration: String?
)

data class LabelDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("catno")
    val catno: String?
)

data class FormatDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("qty")
    val qty: String?,
    @SerializedName("descriptions")
    val descriptions: List<String>?
)