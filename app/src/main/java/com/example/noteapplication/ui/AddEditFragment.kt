package com.example.noteapplication.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.noteapplication.R
import com.example.noteapplication.databinding.FragmentAddEditBinding
import com.example.noteapplication.model.Note
import com.example.noteapplication.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*


class AddEditFragment : Fragment() {
    lateinit var viewModel: NoteViewModel
    lateinit var binding: FragmentAddEditBinding
    var noteID = -1

    companion object {
        fun newInstance(): AddEditFragment {
            return AddEditFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = requireActivity().intent
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
        ).get(NoteViewModel::class.java)

        val noteType = bundle?.getStringExtra(getString(R.string.note_type))
        if (noteType.equals(getString(R.string.edit))) {

            val note = bundle?.getParcelableExtra<Note>(getString(R.string.note_object))
            val noteTitle = note?.noteTitle
            val noteDescription = note?.noteDescription
            noteID = note?.id!!
            binding.idBtn.text = getString(R.string.update)
            binding.idEditNoteName.setText(noteTitle)
            binding.idEditNoteDescription.setText(noteDescription)
        } else {
            binding.idBtn.text = getString(R.string.save)
        }

        binding.idBtn.setOnClickListener {

            val noteTitle = binding.idEditNoteName.text.toString()
            val noteDescription = binding.idEditNoteDescription.text.toString()

            if (noteType.equals(getString(R.string.edit))) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat(getString(R.string.date_format))
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(
                        activity,
                        getString(R.string.note_updated) + " ${updatedNote.noteTitle} , ${updatedNote.noteDescription}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat(getString(R.string.date_format))
                    val currentDateAndTime: String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                    Toast.makeText(activity, "$noteTitle " + getString(R.string.added), Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(context, MainActivity::class.java))
        }
    }


}