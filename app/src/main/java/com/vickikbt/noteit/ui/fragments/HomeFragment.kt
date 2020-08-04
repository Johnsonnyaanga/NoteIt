package com.vickikbt.noteit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.vickikbt.noteit.R
import com.vickikbt.noteit.adapters.NotesRecyclerViewAdapter
import com.vickikbt.noteit.databinding.FragmentHomeBinding
import com.vickikbt.noteit.db.AppDatabase
import com.vickikbt.noteit.db.Note
import com.vickikbt.noteit.repository.NoteRepository
import com.vickikbt.noteit.ui.viewmodels.NoteViewModeFactory
import com.vickikbt.noteit.ui.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val appDatabase = AppDatabase(requireActivity())
        val noteRepository = NoteRepository(appDatabase)
        val factory = NoteViewModeFactory(noteRepository)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this, factory).get(NoteViewModel::class.java)

        initUI()
        initRecyclerView()

        return binding.root
    }

    private fun initUI() {
        binding.floatingActionButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_fragment_home_to_fragment_edit_note)
        }
    }

    private fun initRecyclerView() {
        val noteList = arrayListOf<Note>()
        val adapter = NotesRecyclerViewAdapter(noteList, requireActivity())

        viewModel.allNotes.observe(viewLifecycleOwner, Observer {
            //val notes = it.value

            for (i in it.indices) {
                noteList.add(it[i])
                binding.recyclerviewHome.adapter = adapter
            }
        })

    }


}