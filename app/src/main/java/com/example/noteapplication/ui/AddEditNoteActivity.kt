package com.example.noteapplication.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapplication.R


class AddEditNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.root_layout,
                    AddEditFragment.newInstance(),
                    getString(R.string.add_edit_fragment)
                )
                .commit()
        }
    }
}