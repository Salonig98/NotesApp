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
import android.widget.RadioButton as RadioButton1


class AddEditFragment : Fragment() {
    lateinit var viewModel: NoteViewModel
    lateinit var binding: FragmentAddEditBinding
    var noteID = -1
    var selectedRadioButton: RadioButton1? = null
    var selectedRadioButtonId: Int = -1
    var type: String? = null

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
            val noteTypeText = note?.noteType
            noteID = note?.id!!
            binding.idBtn.text = getString(R.string.update)
            binding.idEditNoteName.setText(noteTitle)
            binding.idEditNoteDescription.setText(noteDescription)

            val radiobuttonFamily = binding.family
            val radiobuttonOffice = binding.office
            val radiobuttonHousehold = binding.household

            if (noteTypeText.equals(getString(R.string.action_family), ignoreCase = true)) {
                radiobuttonFamily.isChecked = true
            } else if (noteTypeText.equals(getString(R.string.action_office), ignoreCase = true)) {
                radiobuttonOffice.isChecked = true
            } else {
                radiobuttonHousehold.isChecked = true
            }
        } else {
            binding.idBtn.text = getString(R.string.save)
        }

        binding.idBtn.setOnClickListener {

            selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = binding.radioGroup.findViewById(selectedRadioButtonId)
                val selectedRbText: String = selectedRadioButton?.text.toString()
                type = selectedRbText
            }

            val noteTitle = binding.idEditNoteName.text.toString()
            val noteDescription = binding.idEditNoteDescription.text.toString()
            val noteFilterType = type

            if (noteType.equals(getString(R.string.edit))) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val dateFormat = SimpleDateFormat(getString(R.string.date_format))
                    val currentDateAndTime: String = dateFormat.format(Date())
                    val updatedNote =
                        Note(noteTitle, noteFilterType, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(
                        activity,
                        getString(R.string.note_updated) + " ${updatedNote.noteTitle} , ${updatedNote.noteDescription}, ${updatedNote.noteType}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                if (noteFilterType != null) {
                    if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty() && noteFilterType.isNotEmpty()) {
                        val simpleDateFormat = SimpleDateFormat(getString(R.string.date_format))
                        val currentDateAndTime: String = simpleDateFormat.format(Date())
                        viewModel.addNote(
                            Note(
                                noteTitle,
                                noteFilterType,
                                noteDescription,
                                currentDateAndTime
                            )
                        )
                        Toast.makeText(
                            activity,
                            "$noteTitle , $noteType " + getString(R.string.added),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
            startActivity(Intent(context, MainActivity::class.java))
        }
    }


}