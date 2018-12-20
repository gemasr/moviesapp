package com.gemasr.moviesapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalMovie::class], version = 1, exportSchema = true)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun MoviesDao():MoviesDao

}