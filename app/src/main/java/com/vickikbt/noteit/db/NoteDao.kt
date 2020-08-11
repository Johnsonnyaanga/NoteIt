package com.vickikbt.noteit.db

import androidx.room.*

@Dao
interface NoteDao {

    //Insert or update a note into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: Note)

    //Delete note from the database
    @Delete
    suspend fun delete(note: Note)

    //Get all notes that are in the database and order them using their ids in a descending order(newest first).
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    suspend fun getAllNotes(): List<Note>

}