package com.example.dannywright.vinylvault.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dannywright.vinylvault.domain.model.Release
import com.example.dannywright.vinylvault.domain.repository.VinylRepository
import com.example.dannywright.vinylvault.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: VinylRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    fun searchReleases(query: String) {
        if (query.isBlank()) return

        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            when (val result = repository.searchReleases(query)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        releases = result.data ?: emptyList(),
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

    fun updateSearchQuery(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
    }
}

data class SearchState(
    val searchQuery: String = "",
    val releases: List<Release> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)