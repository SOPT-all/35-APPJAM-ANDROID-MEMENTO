package org.memento.presentation.plusbottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.R
import org.memento.core.util.UiState
import org.memento.domain.entity.BrainDump
import org.memento.domain.repository.AddPlanRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BrainDumpViewModel
    @Inject
    constructor(
        val addPlanRepository: AddPlanRepository,
    ) : ViewModel() {
        private val _dummyTexts =
            persistentListOf(
                R.string.brain_dump_example_1,
                R.string.brain_dump_example_2,
                R.string.brain_dump_example_3,
                R.string.brain_dump_example_4,
            )
        val dummyTexts: PersistentList<Int> = _dummyTexts

        private val _inputText = MutableStateFlow("")
        val inputText: StateFlow<String> = _inputText

        private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val uiState: StateFlow<UiState<Unit>> = _uiState

        fun setLoadingState() {
            _uiState.value = UiState.Loading
        }

        fun updateInputText(newText: String) {
            _inputText.value = newText
        }

        fun pasteCipBoard(clipboardText: String?) {
            clipboardText?.let {
                _inputText.value = it
            }
        }

        fun postBrainDump() {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                val result =
                    addPlanRepository.postBrainDump(
                        BrainDump(
                            content = _inputText.value,
                        ),
                    )
                _uiState.value =
                    result.fold(
                        onSuccess = {
                            UiState.Success(Unit)
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "Failed to post brain dump")
                            UiState.Failure
                        },
                    )
            }
        }

        fun resetData() {
            _inputText.value = ""
        }
    }
