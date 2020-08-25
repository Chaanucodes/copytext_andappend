package com.example.mycollections

import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.mycollections.database.NoteDatabase

class ProcessTextActivity : AppCompatActivity() {

    private lateinit var processViewModel : ProcessTextViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_text)

        val application = requireNotNull(this).application
        val dataSource = NoteDatabase.getInstance(application).notesDatabaseDao

        val viewModelFactory = ProcessTextViewModelFactory(dataSource, application)
        processViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ProcessTextViewModel::class.java)
        val notificationManager = applicationContext.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.cancelAll()
    }

    fun navigateToMain(view: View) {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }
}