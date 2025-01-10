package org.memento.presentation.reqres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.model.Reqres
import org.memento.domain.repository.ReqresRepository
import javax.inject.Inject

@HiltViewModel
class ReqresViewModel
    @Inject
    constructor(
        private val reqresRepository: ReqresRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<UiState<List<Reqres>>>(UiState.Loading)
        val uiState: StateFlow<UiState<List<Reqres>>> = _uiState

        fun fetchReqresData(page: Int) {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                val result = reqresRepository.getReqresLists(page)
                _uiState.value =
                    result.fold(
                        onSuccess = { data -> UiState.Success(data) },
                        onFailure = { UiState.Failure },
                    )
            }
        }
    }
