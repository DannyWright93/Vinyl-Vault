package com.example.dannywright.vinylvault.data.repository

import com.example.dannywright.vinylvault.data.local.entity.ReleaseEntity
import com.example.dannywright.vinylvault.data.remote.dto.DiscogsReleaseDetailDto
import com.example.dannywright.vinylvault.data.remote.dto.DiscogsSearchResultDto
import com.example.dannywright.vinylvault.domain.model.CollectionRelease
import com.example.dannywright.vinylvault.domain.model.Release
import com.example.dannywright.vinylvault.domain.model.ReleaseDetail
import com.example.dannywright.vinylvault.domain.model.Track

fun DiscogsSearchResultDto.toRelease(): Release {
    return Release(
        id = id,
        title = title,
        artist = extractArtist(title),
        year = year,
        coverImageUrl = coverImage ?: thumb,
        genres = genre ?: emptyList(),
        styles = style ?: emptyList(),
        format = format?.joinToString(", "),
        country = country,
        label = label?.firstOrNull()
    )
}

fun DiscogsReleaseDetailDto.toReleaseDetail(): ReleaseDetail {
    return ReleaseDetail(
        id = id,
        title = title,
        artists = artists?.map { it.name } ?: emptyList(),
        year = year,
        genres = genres ?: emptyList(),
        styles = styles ?: emptyList(),
        coverImageUrl = images?.firstOrNull { it.type == "primary" }?.uri
            ?: images?.firstOrNull()?.uri,
        tracklist = tracklist?.map {
            Track(
                position = it.position,
                title = it.title,
                duration = it.duration
            )
        } ?: emptyList(),
        labels = labels?.map { it.name } ?: emptyList(),
        country = country,
        released = released,
        notes = notes,
        format = formats?.firstOrNull()?.let { format ->
            buildString {
                append(format.name)
                format.descriptions?.let { desc ->
                    if (desc.isNotEmpty()) {
                        append(" (${desc.joinToString(", ")})")
                    }
                }
            }
        }
    )
}


fun Release.toEntity(addedDate: Long = System.currentTimeMillis()): ReleaseEntity {
    return ReleaseEntity(
        id = id,
        title = title,
        artist = artist,
        year = year,
        coverImageUrl = coverImageUrl,
        genres = genres,
        format = format,
        addedDate = addedDate
    )
}


fun ReleaseEntity.toCollectionRelease(): CollectionRelease {
    return CollectionRelease(
        id = id,
        title = title,
        artist = artist,
        year = year,
        coverImageUrl = coverImageUrl,
        genres = genres,
        format = format,
        addedDate = addedDate
    )
}


private fun extractArtist(title: String): String {
    return if (title.contains(" - ")) {
        title.substringBefore(" - ").trim()
    } else {
        "Unknown Artist"
    }
}