package com.example.mycollections

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mycollections.database.NoteDao
import com.example.mycollections.database.NoteDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class MainActivityViewModel(private val database : NoteDao, application : Application) : AndroidViewModel(application) {

    val flowOfStrings = database.getAllStrings()

    private var _mainText = MutableLiveData<String>()
    val mainText : LiveData<String>
    get() = _mainText

    val job = Job()
    val scope = CoroutineScope(Dispatchers.Main + job)




    fun letsDeleteData() {
        scope.launch {
            withContext(Dispatchers.IO){
                database.deleteData()
                Log.i("okla", "cheest")
            }
        }
    }
}

class TitleFragViewModelFactory(
    private val dataSource : NoteDao,
    private val application: Application) : ViewModelProvider.Factory {
    @InternalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}