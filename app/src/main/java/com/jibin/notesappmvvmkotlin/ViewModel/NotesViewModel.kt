package com.jibin.notesappmvvmkotlin.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jibin.notesappmvvmkotlin.Database.NotesDatabase
import com.jibin.notesappmvvmkotlin.Model.Notes
import com.jibin.notesappmvvmkotlin.Repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun addNotes(notes: Notes) {
        repository.insertNotes(notes)
    }

    fun deleteNotes(id: Int) {
        repository.deleteNotes(id)
    }

    fun getNotes(): LiveData<List<Notes>> = repository.getAllNotes()

    fun getHighNotes() = repository.getHighNotes()

    fun getMediumNotes() = repository.getMediumNotes()

    fun getLowNotes() = repository.getLowNotes()


    fun updateNotes(notes: Notes) {
        repository.updateNotes(notes)
    }

}