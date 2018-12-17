package com.gemasr.moviesapp.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [LocalMovie::class], version = 1)
abstract class MoviesDatabase:RoomDatabase() {

    abstract fun MoviesDao():MoviesDao

}