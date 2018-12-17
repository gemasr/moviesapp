package com.gemasr.moviesapp.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Insert
    fun insertMovie(movie:LocalMovie): Completable

    @Query("SELECT * from movies WHERE id = :id AND type = :type")
    fun findMovie(id:String, type:Int): Single<LocalMovie>

    @Query("SELECT * from movies WHERE page = :page AND type = 1")
    fun getPopularMovies(page:Int):Single<List<LocalMovie>>

    @Query("SELECT * from movies WHERE page = :page AND type = 2")
    fun getTopRatedMovies(page:Int):Single<List<LocalMovie>>

    @Query("SELECT * from movies WHERE page = :page AND type = 3")
    fun getUpcomingMovies(page:Int):Single<List<LocalMovie>>


}