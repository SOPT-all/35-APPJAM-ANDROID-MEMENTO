package org.memento.presentation.plusbottomsheet

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.memento.R
import javax.inject.Inject

@HiltViewModel
class BrainDumpViewModel
    @Inject
    constructor() : ViewModel() {
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

        fun updateInputText(newText: String) {
            _inputText.value = newText
        }

        fun pasteCipBoard(clipboardText: String?) {
            clipboardText?.let {
                _inputText.value = it
            }
        }
    }
