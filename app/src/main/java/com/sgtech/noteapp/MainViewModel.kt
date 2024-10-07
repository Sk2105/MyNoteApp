package com.sgtech.noteapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgtech.noteapp.data.local.entities.Note
import com.sgtech.noteapp.data.local.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.onStart {
        fetchNotes()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private fun fetchNotes() {
        viewModelScope.launch {
            repository.getNotes().collect{
                _notes.value = it
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun getFavouriteNotes() = repository.getFavoriteNotes()



    fun getNoteById(id: Int) = repository.getNoteById(id)
}