package com.sgtech.noteapp.di

import android.content.Context
import androidx.room.Room
import com.sgtech.noteapp.data.local.NoteDatabase
import com.sgtech.noteapp.data.local.dao.NoteDao
import com.sgtech.noteapp.data.local.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNoteDB(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, "NoteDB").build()
    }

    @Provides
    fun provideNoteDao(roomDatabase: NoteDatabase): NoteDao {
        return roomDatabase.noteDao()
    }

    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }




}