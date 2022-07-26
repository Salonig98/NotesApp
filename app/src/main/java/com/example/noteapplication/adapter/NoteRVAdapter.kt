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

class NoteRVAdapter(
    val context: Context,
    private val noteClickInterface: NoteClickInterface,
    private val noteClickDeleteInterface: NoteClickDeleteInterface,
    private val noteClickShareInterface: NoteClickShareInterface,
    ) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    private var allNotes = ArrayList<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTV: TextView = itemView.findViewById<TextView>(R.id.id_tv_note_title)
        val dateTV: TextView = itemView.findViewById<TextView>(R.id.id_tv_timestamp)
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
        holder.noteTV.text = allNotes.get(position).noteTitle
        holder.dateTV.text = "Last Updated : " + allNotes[position].timestamp
        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
        }
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes[position])
        }
        holder.itemView.setOnClickListener {
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
        this.allNotes = noteList
        notifyDataSetChanged()
    }


}