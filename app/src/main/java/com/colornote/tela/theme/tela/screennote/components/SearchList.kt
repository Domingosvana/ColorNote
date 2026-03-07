package com.colornote.tela.theme.tela.screennote

import androidx.lifecycle.ViewModel
import com.colornote.feature_note.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope

class SearchViewModel : ViewModel() {

    private val _allNotes = MutableStateFlow<List<Note>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val searchResults: StateFlow<List<Note>> = combine(_allNotes, _searchQuery) { notes, query ->
        if (query.isBlank()) notes
        else notes.filter {
            it.title.contains(query, ignoreCase = true) ||
                    (it.content?.contains(query, ignoreCase = true) ?: false)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(
            5000),
        emptyList()
    )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun updateAllNotes(newNotes: List<Note>) {
        _allNotes.value = newNotes
    }
}
