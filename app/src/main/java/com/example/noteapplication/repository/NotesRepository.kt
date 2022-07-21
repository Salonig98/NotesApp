package com.example.noteapplication.repository

import androidx.lifecycle.LiveData
import com.example.noteapplication.dao.NotesDao
import com.example.noteapplication.model.Note

class NotesRepository (private val notesDao : NotesDao){

    val allNotes : LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note : Note){
        notesDao.insert(note)
    }

    suspend fun update(note : Note){
        notesDao.update(note)
    }

    suspend fun delete(note : Note){
        notesDao.delete(note)
    }
}