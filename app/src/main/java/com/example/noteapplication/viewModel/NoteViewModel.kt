package com.example.noteapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapplication.database.NoteDatabase
import com.example.noteapplication.model.Note
import com.example.noteapplication.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Note>>
    private val repository: NotesRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NotesRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun searchNote(data: String): LiveData<List<Note>>? {
        return repository.search(data)
    }
}

