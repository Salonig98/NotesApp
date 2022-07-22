package com.example.noteapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.R
import com.example.noteapplication.databinding.ActivityMainBinding
import com.example.noteapplication.databinding.NoteRvItemBinding
import com.example.noteapplication.model.Note

class NoteRVAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteClickDeleteInterface: NoteClickDeleteInterface
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>(){

    private val allNotes = ArrayList<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTV = itemView.findViewById<TextView>(R.id.IDTVNoteTitle)
        val dateTV = itemView.findViewById<TextView>(R.id.IDTVTimeStamp)
        val deleteIV = itemView.findViewById<ImageView>(R.id.IDTVDelete)
    }
interface NoteClickDeleteInterface {
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    fun onNoteClick(note: Note)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTV.setText(allNotes.get(position).noteTitle)
        holder.dateTV.setText("Last Updated : " + allNotes.get(position).timestamp)
        holder.deleteIV.setOnClickListener {
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }

    override fun getItemCount(): Int {
      return allNotes.size
    }

    fun updateList(newList : List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}