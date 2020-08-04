package com.vickikbt.noteit.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.vickikbt.noteit.R
import com.vickikbt.noteit.databinding.FragmentNoteBinding
import com.vickikbt.noteit.db.AppDatabase
import com.vickikbt.noteit.repository.NoteRepository
import com.vickikbt.noteit.ui.activities.MainActivity
import com.vickikbt.noteit.ui.viewmodels.NoteViewModeFactory
import com.vickikbt.noteit.ui.viewmodels.NoteViewModel
import com.vickikbt.noteit.util.NoteListener
import com.vickikbt.noteit.util.toast

class NoteFragment : Fragment(), NoteListener {

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

        binding.viewModel = viewModel

        viewModel.noteListener = this

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                viewModel.saveNote()
            }
        }
        return false
    }

    override fun onStarted() {
        binding.progressBarNote.visibility = View.VISIBLE
    }

    override fun onSuccess(message: String) {
        binding.progressBarNote.visibility = View.GONE
        context?.toast(message)

        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }

    override fun onFailure(message: String) {
        binding.progressBarNote.visibility = View.GONE
        context?.toast(message)
    }


}