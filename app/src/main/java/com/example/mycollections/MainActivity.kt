package com.example.mycollections

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mycollections.database.NoteDatabase
import com.example.mycollections.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel : MainActivityViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val application = requireNotNull(this).application
        val dataSource = NoteDatabase.getInstance(application).notesDatabaseDao

        val viewModelFactory = TitleFragViewModelFactory(dataSource, application)
        mainViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
//        startService(Intent(this, Clipboard::class.java))

        mainViewModel.flowOfStrings.observe(this, Observer {
            it.forEach {
                binding.noteText.text = "${binding.noteText.text} \n ${it.note}"
            }
            if (it.isEmpty()){
                binding.noteText.text = ""
            }
            binding.noteText.text = "${binding.noteText.text} ${it.size}"
        })

        binding.buttonDel.setOnClickListener {
            mainViewModel.letsDeleteData()
        }
    }

}