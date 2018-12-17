package com.gemasr.moviesapp.data.source.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.gemasr.moviesapp.data.model.Movie

@Entity(tableName = "movies")
data class LocalMovie (@PrimaryKey val id: Int,
                       val posterpath:String?,
                       val adult:Boolean,
                       val overview:String,
                       val releaseDate:String,
                       val genreIds:List<String>,
                       val originalTitle: String,
                       val originalLanguage: String,
                       val title:String,
                       val backdropPath:String?,
                       val popularity:Float,
                       val voteCount: Int,
                       val video:Boolean,
                       val voteAverage: Float,
                       val type: Int,
                       val page: Int
){


    fun getMovieFromLocalMovie():Movie{
        return Movie(id = id,
                posterpath = posterpath,
                adult = adult,
                overview = overview,
                releaseDate = releaseDate,
                genreIds = genreIds,
                originalTitle = originalTitle,
                originalLanguage = originalLanguage,
                title = title,
                backdropPath = backdropPath,
                popularity = popularity,
                voteCount = voteCount,
                video = video,
                voteAverage = voteAverage
                )
    }

    companion object {
        fun buildLocalMovieFromMovie(remoteMovie: Movie, page:Int, type:MovieListType):LocalMovie{
            return LocalMovie(
                    id = remoteMovie.id,
                    posterpath = remoteMovie.posterpath,
                    adult = remoteMovie.adult,
                    overview = remoteMovie.overview,
                    releaseDate = remoteMovie.releaseDate,
                    genreIds = remoteMovie.genreIds,
                    originalTitle = remoteMovie.originalTitle,
                    originalLanguage = remoteMovie.originalLanguage,
                    title = remoteMovie.title,
                    backdropPath = remoteMovie.backdropPath,
                    popularity = remoteMovie.popularity,
                    voteCount = remoteMovie.voteCount,
                    video = remoteMovie.video,
                    voteAverage = remoteMovie.voteAverage,
                    type = type.value,
                    page = page
            )
        }
    }

}