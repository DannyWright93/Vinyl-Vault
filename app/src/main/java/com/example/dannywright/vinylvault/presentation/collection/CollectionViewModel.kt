package com.example.dannywright.vinylvault.presentation.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dannywright.vinylvault.domain.model.CollectionRelease
import com.example.dannywright.vinylvault.domain.repository.VinylRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repository: VinylRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CollectionState())
    val state: StateFlow<CollectionState> = _state.asStateFlow()

    init {
        loadCollection()
    }

    private fun loadCollection() {
        viewModelScope.launch {
            repository.getCollection().collect { releases ->
                _state.value = _state.value.copy(
                    releases = releases,
                    isLoading = false
                )
            }
        }
    }

    fun removeFromCollection(releaseId: Int) {
        viewModelScope.launch {
            repository.removeFromCollection(releaseId)
        }
    }
}

data class CollectionState(
    val releases: List<CollectionRelease> = emptyList(),
    val isLoading: Boolean = true
)