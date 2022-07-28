package com.example.noteapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapplication.R
import com.example.noteapplication.adapter.NoteRVAdapter
import com.example.noteapplication.model.Note
import com.example.noteapplication.viewModel.NoteViewModel

class MainActivity : AppCompatActivity(), NoteRVAdapter.NoteClickInterface,
    NoteRVAdapter.NoteClickDeleteInterface, NoteRVAdapter.NoteClickShareInterface {
    private lateinit var binding: com.example.noteapplication.databinding.ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel
    private lateinit var searchView: SearchView
    private lateinit var noteAdapter: NoteRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, com.example.noteapplication.R.layout.activity_main)
        binding.idRvNotes.layoutManager = LinearLayoutManager(this)

        noteAdapter = NoteRVAdapter(this, this, this, this)
        binding.idRvNotes.adapter = noteAdapter
        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        noteViewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                noteAdapter.updateList(it)
            }
        })

        binding.idFabAddNote.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val search = menu?.findItem(R.id.searchItems)
        searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    getItemsFromDB(query)

                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null)
                    getItemsFromDB(newText)
                return true
            }
        })
        return true
    }

    private fun getItemsFromDB(data: String) {
        var searchText = data
        searchText = "%$data%"
        noteViewModel.search(searchText)?.observe(this, Observer {
            noteAdapter.setData(it as ArrayList<Note>)

        })
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra(getString(R.string.note_object), note)
        intent.putExtra(getString(R.string.note_type), getString(R.string.edit))
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {
        noteViewModel.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} " + getString(R.string.deleted), Toast.LENGTH_LONG).show()
    }

    override fun onShareIconClick(note: Note) {
        var emailSend = getString(R.string.email)
        var emailSubject: String? = note.noteTitle
        var emailBody = note.noteDescription

        val intent: Intent = Intent(Intent.ACTION_SEND)

        intent.putExtra(Intent.EXTRA_EMAIL, emailSend)
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        intent.putExtra(Intent.EXTRA_TEXT, emailBody)

        intent.type = getString(R.string.message_code)

        startActivity(
            Intent
                .createChooser(
                    intent,
                    getString(R.string.choose_an_email_client)
                )
        )
    }
}
