package com.example.noteapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.R
import com.example.noteapplication.model.Note
import com.example.noteapplication.ui.MainActivity

class NoteRVAdapter(
    val context: Context,
    private val noteClickInterface: MainActivity,
    private val noteClickDeleteInterface: NoteClickDeleteInterface,
    private val noteClickShareInterface: NoteClickShareInterface,
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    private var allNotes = ArrayList<Note>()
    private val lastUpdated: String = "Last Updated : "

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTV: TextView = itemView.findViewById<TextView>(R.id.id_tv_note_title)
        val dateTV: TextView = itemView.findViewById<TextView>(R.id.id_tv_timestamp)
        val noteTypeTV: TextView = itemView.findViewById<TextView>(R.id.tv_note_type)
        val deleteIV: ImageView = itemView.findViewById<ImageView>(R.id.id_tv_delete)
        val shareIV: ImageView = itemView.findViewById<ImageView>(R.id.id_tv_share)
    }

    interface NoteClickDeleteInterface {
        fun onDeleteIconClick(note: Note)
    }

    interface NoteClickInterface {
        fun onNoteClick(note: Note)
    }

    interface NoteClickShareInterface {
        fun onShareIconClick(note: Note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = allNotes.get(position)
        holder.noteTV.text = note.noteTitle
        holder.dateTV.text = lastUpdated + note.timestamp
        holder.noteTypeTV.text = note.noteType
        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
        }
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes[position])
        }
        holder.shareIV.setOnClickListener {
            noteClickShareInterface.onShareIconClick(allNotes[position])
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    fun setData(noteList: ArrayList<Note>) {
        allNotes.clear()
        this.allNotes = noteList
        notifyDataSetChanged()
    }

    fun showOfficeNotes(noteType: String) {
        var tempList = allNotes
        var noteList = ArrayList<Note>()
        for (note in allNotes) {

        }

    }
//        println("Inside showOffice")
//        var noteList = ArrayList<Note>()
//        this.allNotes.forEach { note ->
//            if (noteType == "Office") {
//                noteList.add(note)
//            } else if (noteType == "Family") {
//                noteList.add(note)
//            } else if (noteType == "Household") {
//                noteList.add(note)
//            }
//        }
//        if(noteList != null) {
//            println("Inside the null check")
//            this.allNotes = noteList
//            for(note in noteList){
//                println("this alos  , ${note.timestamp}, ${note.noteTitle}, ${note.noteType}")
//            }
//        } else{
//            Log.v("Adapter","the list is null")
//        }
//
//        for(note in noteList){
//            note.noteType?.let { Log.d("adapter", it) }
//        }
//
//    }


}