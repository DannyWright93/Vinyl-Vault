package com.example.dannywright.vinylvault.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DiscogsSearchResponse(
    @SerializedName("pagination")
    val pagination: PaginationDto?,
    @SerializedName("results")
    val results: List<DiscogsSearchResultDto>
)

data class PaginationDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("items")
    val items: Int
)

data class DiscogsSearchResultDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("year")
    val year: String?,
    @SerializedName("thumb")
    val thumb: String?,
    @SerializedName("cover_image")
    val coverImage: String?,
    @SerializedName("genre")
    val genre: List<String>?,
    @SerializedName("style")
    val style: List<String>?,
    @SerializedName("format")
    val format: List<String>?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("label")
    val label: List<String>?,
    @SerializedName("catno")
    val catno: String?,
    @SerializedName("type")
    val type: String?
)