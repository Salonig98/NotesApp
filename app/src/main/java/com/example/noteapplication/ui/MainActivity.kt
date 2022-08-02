package com.example.noteapplication.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapplication.R
import com.example.noteapplication.adapter.NoteRVAdapter
import com.example.noteapplication.model.FilterType
import com.example.noteapplication.model.Note
import com.example.noteapplication.viewModel.NoteViewModel


class MainActivity : AppCompatActivity(), NoteRVAdapter.NoteClickInterface,
    NoteRVAdapter.NoteClickDeleteInterface, NoteRVAdapter.NoteClickShareInterface,
    SearchView.OnQueryTextListener {
    private lateinit var binding: com.example.noteapplication.databinding.ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar


        actionBar!!.title = getString(R.string.notes_app)
        actionBar.subtitle = getString(R.string.save_all_notes)
        actionBar.setIcon(R.drawable.ic_notes_logo)

        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                noteViewModel.allNotes.observe(this, Observer { list ->
                    list?.let {
                        noteAdapter.updateList(it)
                    }
                })
            }
            R.id.menu_filter -> {
                val menuItemView = findViewById<View>(R.id.menu_filter)
                showFilteringPopUpMenu(menuItemView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showFilteringPopUpMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.popup_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.action_office -> {
                    getNotesFromDB(FilterType.Office.toString())
                }
                R.id.action_family -> {
                    getNotesFromDB(FilterType.Family.toString())
                }
                R.id.action_household -> {
                    getNotesFromDB(FilterType.Household.toString())
                }
            }

            true
        })

        popup.show()
    }

    private fun getNotesFromDB(data: String) {
        var searchText = data
        Log.d("MainActivity", "Inside getNotesFromDB")
        searchText = "%$data%"
        noteViewModel.search(searchText)?.observe(this, Observer {
            noteAdapter.setData(it as ArrayList<Note>)
        })
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra(getString(com.example.noteapplication.R.string.note_object), note)
        intent.putExtra(
            getString(com.example.noteapplication.R.string.note_type),
            getString(com.example.noteapplication.R.string.edit)
        )
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {
        noteViewModel.deleteNote(note)
        Toast.makeText(
            this,
            "${note.noteTitle} " + getString(com.example.noteapplication.R.string.deleted),
            Toast.LENGTH_LONG
        )
            .show()
    }

    override fun onShareIconClick(note: Note) {
        var emailSend = getString(com.example.noteapplication.R.string.email)
        var emailSubject: String? = note.noteTitle
        var emailBody = note.noteDescription

        val intent: Intent = Intent(Intent.ACTION_SEND)

        intent.putExtra(Intent.EXTRA_EMAIL, emailSend)
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        intent.putExtra(Intent.EXTRA_TEXT, emailBody)

        intent.type = getString(com.example.noteapplication.R.string.message_code)

        startActivity(
            Intent
                .createChooser(
                    intent,
                    getString(com.example.noteapplication.R.string.choose_an_email_client)
                )
        )
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null)
            getNotesFromDB(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null)
            getNotesFromDB(newText)
        return true
    }
}


