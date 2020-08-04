package com.vickikbt.noteit.repository

import com.vickikbt.noteit.db.AppDatabase
import com.vickikbt.noteit.db.Note

class NoteRepository(private val db: AppDatabase) {

    suspend fun upsertNote(note: Note) {
        db.noteDao.upsert(note)
    }

    suspend fun deleteNote(note: Note) {
        db.noteDao.delete(note)
    }

    suspend fun getAllNotes() = db.noteDao.getAllNotes()
}