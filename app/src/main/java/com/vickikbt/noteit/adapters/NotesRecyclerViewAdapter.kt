package com.vickikbt.noteit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.vickikbt.noteit.R
import com.vickikbt.noteit.db.Note
import com.vickikbt.noteit.ui.fragments.HomeFragmentDirections

class NotesRecyclerViewAdapter(private val noteList: List<Note>, private val context: Context) :
    RecyclerView.Adapter<NoteRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)

        return NoteRecyclerViewHolder(view)
    }

    override fun getItemCount() = noteList.size

    override fun onBindViewHolder(holder: NoteRecyclerViewHolder, position: Int) {
        val note = noteList[position]

        holder.title.text = note.title
        holder.description.text = note.description

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionFragmentHomeToFragmentNote(position)

            Navigation.findNavController(it).navigate(action)
        }
    }
}

class NoteRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.textView_title)
    val description: TextView = itemView.findViewById(R.id.textView_description)

}