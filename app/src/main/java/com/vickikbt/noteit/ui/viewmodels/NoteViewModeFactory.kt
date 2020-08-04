package com.vickikbt.noteit.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vickikbt.noteit.repository.NoteRepository

@Suppress("UNCHECKED_CAST")
class NoteViewModeFactory(
    private val noteRepository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(noteRepository) as T
    }

}