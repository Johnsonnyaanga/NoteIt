package com.vickikbt.noteit.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.vickikbt.noteit.R
import com.vickikbt.noteit.databinding.FragmentNoteBinding
import com.vickikbt.noteit.db.AppDatabase
import com.vickikbt.noteit.repository.NoteRepository
import com.vickikbt.noteit.ui.activities.MainActivity
import com.vickikbt.noteit.ui.viewmodels.NoteViewModeFactory
import com.vickikbt.noteit.ui.viewmodels.NoteViewModel
import com.vickikbt.noteit.util.toast

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val appDatabase = AppDatabase(requireActivity())
        val noteRepository = NoteRepository(appDatabase)
        val factory = NoteViewModeFactory(noteRepository)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false)
        viewModel = ViewModelProvider(this, factory).get(NoteViewModel::class.java)
        setHasOptionsMenu(true)

        arguments.let { it ->
            val args = NoteFragmentArgs.fromBundle(it!!)

            viewModel.allNotes.observe(viewLifecycleOwner, Observer { note ->
                val notes = note[args.Position]
                binding.textViewNoteTitle.text = notes.title
                binding.textViewNoteDescription.text = notes.description
            })
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_edit_note->{
                /*val action=NoteFragmentDirections.actionFragmentNoteToFragmentEditNote()
                Navigation.findNavController(requireActivity(), item.itemId).navigate(action)*/
                context?.toast("Not set yet!")
            }

            R.id.action_delete_note->{
                arguments.let { it ->
                    val args = NoteFragmentArgs.fromBundle(it!!)

                    viewModel.allNotes.observe(viewLifecycleOwner, Observer { note ->
                        val notes = note[args.Position]
                        viewModel.deleteNote(notes)
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                        requireActivity().finish()
                    })
                }
            }
        }
        return false
    }

}