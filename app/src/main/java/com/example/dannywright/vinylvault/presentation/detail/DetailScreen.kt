package com.example.dannywright.vinylvault.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dannywright.vinylvault.domain.model.ReleaseDetail
import com.example.dannywright.vinylvault.domain.model.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    releaseId: Int,
    onNavigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(releaseId) {
        viewModel.loadReleaseDetail(releaseId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleCollection() }) {
                        Icon(
                            imageVector = if (state.isInCollection)
                                Icons.Default.Favorite
                            else
                                Icons.Default.FavoriteBorder,
                            contentDescription = if (state.isInCollection)
                                "Remove from collection"
                            else
                                "Add to collection",
                            tint = if (state.isInCollection)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                state.releaseDetail != null -> {
                    ReleaseDetailContent(release = state.releaseDetail!!)
                }
            }
        }
    }
}

@Composable
fun ReleaseDetailContent(release: ReleaseDetail) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cover Image
        item {
            AsyncImage(
                model = release.coverImageUrl,
                contentDescription = release.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        }

        // Title & Artist
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = release.artists.joinToString(", "),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = release.title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        // Basic Info
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    release.year?.let {
                        InfoRow("Year", it.toString())
                    }
                    release.country?.let {
                        InfoRow("Country", it)
                    }
                    release.format?.let {
                        InfoRow("Format", it)
                    }
                    if (release.labels.isNotEmpty()) {
                        InfoRow("Label", release.labels.joinToString(", "))
                    }
                    if (release.genres.isNotEmpty()) {
                        InfoRow("Genres", release.genres.joinToString(", "))
                    }
                    if (release.styles.isNotEmpty()) {
                        InfoRow("Styles", release.styles.joinToString(", "))
                    }
                }
            }
        }

        // Tracklist
        if (release.tracklist.isNotEmpty()) {
            item {
                Text(
                    text = "Tracklist",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            items(release.tracklist) { track ->
                TrackItem(track = track)
            }
        }

        // Notes
        release.notes?.let { notes ->
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Notes",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = notes,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun TrackItem(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = track.position,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = track.title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        track.duration?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}