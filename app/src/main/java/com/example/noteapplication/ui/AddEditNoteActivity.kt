package com.example.noteapplication.ui


import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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