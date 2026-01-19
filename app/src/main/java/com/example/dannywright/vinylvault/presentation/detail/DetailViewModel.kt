package com.example.dannywright.vinylvault.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dannywright.vinylvault.domain.model.Release
import com.example.dannywright.vinylvault.domain.model.ReleaseDetail
import com.example.dannywright.vinylvault.domain.repository.VinylRepository
import com.example.dannywright.vinylvault.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: VinylRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    fun loadReleaseDetail(releaseId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val isInCollection = repository.isInCollection(releaseId)

            when (val result = repository.getReleaseDetail(releaseId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        releaseDetail = result.data,
                        isInCollection = isInCollection,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    fun toggleCollection() {
        viewModelScope.launch {
            val detail = _state.value.releaseDetail ?: return@launch

            if (_state.value.isInCollection) {
                repository.removeFromCollection(detail.id)
                _state.value = _state.value.copy(isInCollection = false)
            } else {
                val release = Release(
                    id = detail.id,
                    title = detail.title,
                    artist = detail.artists.firstOrNull() ?: "Unknown",
                    year = detail.year?.toString(),
                    coverImageUrl = detail.coverImageUrl,
                    genres = detail.genres,
                    styles = detail.styles,
                    format = detail.format,
                    country = detail.country,
                    label = detail.labels.firstOrNull()
                )
                repository.addToCollection(release)
                _state.value = _state.value.copy(isInCollection = true)
            }
        }
    }
}

data class DetailState(
    val releaseDetail: ReleaseDetail? = null,
    val isInCollection: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)