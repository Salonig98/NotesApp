package com.example.noteapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteapplication.model.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notesTable WHERE noteTitle || noteType LIKE :searchQuery")
    fun search(searchQuery: String): LiveData<List<Note>>

}