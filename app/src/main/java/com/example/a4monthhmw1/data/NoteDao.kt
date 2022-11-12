package com.example.a4monthhmw1.data

import androidx.room.*
import com.example.a4monthhmw1.model.NoteModel

@Dao
interface NoteDao {

    @Query("SELECT* FROM notemodel")
    fun getAllNote(): List<NoteModel>

    @Insert
    fun addNote(model: NoteModel)

    @Delete
    fun deleteNote(model: NoteModel)

    @Update
     fun updateNote(model: NoteModel)
}