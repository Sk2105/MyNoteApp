package com.sgtech.noteapp.data.local.repository

import com.sgtech.noteapp.data.local.dao.NoteDao
import com.sgtech.noteapp.data.local.entities.Note


class NoteRepository(private val dao: NoteDao) {

    fun getNotes() = dao.getAllNotes()

    fun getNoteById(id: Int) = dao.getNoteById(id)

    suspend fun insertNote(note: Note) = dao.insert(note)

    suspend fun deleteNote(note: Note) = dao.delete(note)

    suspend fun updateNote(note: Note) = dao.update(note)

    fun searchDatabase(searchQuery: String) = dao.searchNotes(searchQuery)

    fun getFavoriteNotes() = dao.getFavoriteNotes()

    fun getNotesByDate() = dao.getNotesByDate()

    fun getNotesByTitle() = dao.getNotesByTitle()




}