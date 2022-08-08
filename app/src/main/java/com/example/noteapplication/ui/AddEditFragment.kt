package com.example.noteapplication.ui


import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.noteapplication.R
import com.example.noteapplication.Utils.Companion.toast
import com.example.noteapplication.databinding.FragmentAddEditBinding
import com.example.noteapplication.model.Note
import com.example.noteapplication.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*
import android.widget.RadioButton as RadioButton1


class AddEditFragment : Fragment() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var binding: FragmentAddEditBinding
    private var noteID = -1
    private var selectedRadioButton: RadioButton1? = null
    private var selectedRadioButtonId: Int = -1
    private var type: String? = null

    companion object {
        fun newInstance(): AddEditFragment {
            return AddEditFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            FragmentAddEditBinding.inflate(inflater, container, false)
        val bundle = requireActivity().intent
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
        ).get(NoteViewModel::class.java)

        val noteType = bundle?.getStringExtra(getString(R.string.note_type))

        if (noteType.equals(getString(R.string.edit))) {
            passDataFromEditNoteToList(noteType, bundle)
        } else {
            binding.idBtn.text = getString(R.string.save)
        }

        binding.idBtn.setOnClickListener {
            onClickAddUpdateButton(noteType)
        }
        return binding.root
    }

    private fun passDataFromEditNoteToList(noteType: String?, bundle: Intent) {

        val note = bundle?.getParcelableExtra<Note>(getString(R.string.note_object))
        val noteTitle = note?.noteTitle
        val noteDescription = note?.noteDescription
        val noteTypeText = note?.noteType
        noteID = note?.id!!
        binding.idBtn.text = getString(R.string.update)
        binding.idEditNoteName.setText(noteTitle)
        binding.idEditNoteDescription.setText(noteDescription)

        if (noteTypeText.equals(getString(R.string.action_family), ignoreCase = true)) {
            binding.idRbFamily.isChecked = true
        } else if (noteTypeText.equals(getString(R.string.action_office), ignoreCase = true)) {
            binding.idRbOffice.isChecked = true
        } else {
            binding.idRbHousehold.isChecked = true
        }

    }

    private fun onClickAddUpdateButton(noteType: String?) {
        selectedRadioButtonId = binding.idRadioGroup.checkedRadioButtonId
        if (selectedRadioButtonId != -1) {
            selectedRadioButton = binding.idRadioGroup.findViewById(selectedRadioButtonId)
            val selectedRbText: String = selectedRadioButton?.text.toString()
            type = selectedRbText
        }

        if (noteType.equals(getString(R.string.edit)))
            editNote(
                binding.idEditNoteName.text.toString(),
                type,
                binding.idEditNoteDescription.text.toString()
            ) else {
            addNote(
                type,
                binding.idEditNoteName.text.toString(),
                binding.idEditNoteDescription.text.toString(),
            )
        }
        startActivity(Intent(context, MainActivity::class.java))
    }

    private fun editNote(noteTitle: String, noteFilterType: String?, noteDescription: String) {
        if (binding.idEditNoteName.text.toString()
                .isNotEmpty() && binding.idEditNoteDescription.text.toString().isNotEmpty()
        ) {
            val dateFormat = SimpleDateFormat(getString(R.string.date_format))
            val currentDateAndTime: String = dateFormat.format(Date())
            val updatedNote =
                Note(noteTitle, noteFilterType, noteDescription, currentDateAndTime)
            updatedNote.id = noteID
            viewModel.updateNote(updatedNote)
            context?.toast(getString(R.string.note_updated) + " ${updatedNote.noteTitle} , ${updatedNote.noteDescription}, ${updatedNote.noteType}")
        }
    }

    private fun addNote(
        noteFilterType: String?,
        noteTitle: String,
        noteDescription: String,
    ) {
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
                context?.toast("$noteTitle " + getString(R.string.added))
            }
        }

    }


}