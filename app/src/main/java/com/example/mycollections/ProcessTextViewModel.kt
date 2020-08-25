package com.example.mycollections

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mycollections.database.NoteClass
import com.example.mycollections.database.NoteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class ProcessTextViewModel(val database : NoteDao, application : Application) : AndroidViewModel(application){

    init {
        Clipboard.shouldDisplayNotification = false
        try {
            CoroutineScope(Dispatchers.IO).launch {
                database.insert(NoteClass().apply {
                    note = NoteSaver.listOfData.joinToString("\n")
                })
            }
            Toast.makeText(application, "Data saved successfully", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(application, "error : $e", Toast.LENGTH_LONG).show()
        }
    }

}

class ProcessTextViewModelFactory(
    private val dataSource : NoteDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @InternalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProcessTextViewModel::class.java)) {
            return ProcessTextViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}