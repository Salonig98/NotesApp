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
    val repository: NotesRepository

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

    fun search(data:String):LiveData<List<Note>>?
    {
        return repository.search(data)
    }

 // SEARCH FUNCTION , PASS QUERY


}

//create a field in viewmodel - which will
//save the list in the viewmodel property
//kotlin x dependency for filter
//how to create a regex
//all the logic should be in the viewmodel
//pass the list bac to the UI
//set the new list in the adapter and notify set data change

//getting the filtered list from the db itself
//


//2nd way to search implement
//live search - for later implementaion