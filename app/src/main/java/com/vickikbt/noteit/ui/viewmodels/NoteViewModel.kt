package com.vickikbt.noteit.ui.viewmodels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.vickikbt.noteit.db.Note
import com.vickikbt.noteit.repository.NoteRepository
import com.vickikbt.noteit.util.NoteListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository) : ViewModel(), Observable {

    @Bindable
    val title = MutableLiveData<String>()

    @Bindable
    val description = MutableLiveData<String>()

    var noteListener: NoteListener? = null

    fun saveNote() = viewModelScope.launch {
        noteListener?.onStarted()
        if (title.value.isNullOrEmpty()) {
            noteListener?.onFailure("Enter note title!")
            return@launch
        } else if (description.value.isNullOrEmpty()) {
            noteListener?.onFailure("Enter note description!")
            return@launch
        }

        try {
            noteRepository.upsertNote(Note(0, title.value!!, description.value!!))
            noteListener?.onSuccess("Note Saved")
            return@launch
        } catch (e: Exception) {
            noteListener?.onFailure("Error: $e")
        }
    }

    val allNotes= liveData(Dispatchers.IO){
        noteListener?.onStarted()
        val notes=noteRepository.getAllNotes()
        emit(notes)
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}


}