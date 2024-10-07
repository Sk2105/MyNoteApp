package com.sgtech.noteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sgtech.noteapp.data.local.dao.NoteDao
import com.sgtech.noteapp.data.local.entities.Note


@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteDatabase() : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}