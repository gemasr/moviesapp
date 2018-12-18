package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.mvibase.MviIntent

sealed class MovieDetailIntent: MviIntent {
    data class LoadMovieIntent(val id:Int, val type:MovieListType):MovieDetailIntent()
}