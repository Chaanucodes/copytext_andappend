package com.example.mycollections.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    fun insert(note : NoteClass)

    @Query("delete from my_table")
    fun deleteData()

    @Query("select * from my_table order by id")
    fun getAllStrings() : LiveData<List<NoteClass>>
}