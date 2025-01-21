package org.memento.presentation.plusbottomsheet

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddToDoViewModel
    @Inject
    constructor() : ViewModel() {
        private val _selectedDateText = MutableStateFlow("Today")
        val selectedDateText: StateFlow<String> = _selectedDateText

        private val _addToDoText = MutableStateFlow("")
        val addToDoText: StateFlow<String> = _addToDoText

        private val _addTagColor = MutableStateFlow("#F0F0F3")
        val addTagColor: StateFlow<String> = _addTagColor

        private val _deadLineText = MutableStateFlow("Add DeadLine")
        val deadLineText: StateFlow<String> = _deadLineText

        private val _tempDeadLineText = MutableStateFlow("Today")
        val tempDeadLineText: StateFlow<String> = _tempDeadLineText

        private val _tempTagColor = MutableStateFlow("#F0F0F3")
        val tempTagColor: StateFlow<String> = _tempTagColor

        private val _tempTagText = MutableStateFlow("Untitled")
        val tempTagText: StateFlow<String> = _tempTagText

        fun updateToDoText(newText: String) {
            _addToDoText.value = newText
        }

        fun updateSelectedDateText(newDate: String) {
            _selectedDateText.value = newDate
        }

        fun updateTagColor(newColor: String) {
            _addTagColor.value = newColor
        }

        fun updateDeadLineText(newDeadLine: String) {
            _deadLineText.value = newDeadLine
        }

        fun updateTempDeadLineText(tempDeadLine: String) {
            _tempDeadLineText.value = tempDeadLine
        }

        fun updateTempTagData(
            tempTagColor: String,
            tempTagText: String,
        ) {
            _tempTagColor.value = tempTagColor
            _tempTagText.value = tempTagText
        }
    }
