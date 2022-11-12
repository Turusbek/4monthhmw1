package com.example.a4monthhmw1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a4monthhmw1.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getDao(): NoteDao

    companion object {
        private lateinit var INSTANCE: NoteDatabase

        fun getDbInstance(context: Context): NoteDatabase {

            INSTANCE = Room.databaseBuilder(
                context,
                NoteDatabase::class.java,
                "DB NAME"
            ).allowMainThreadQueries()
                .build()

            return INSTANCE
        }
    }
}