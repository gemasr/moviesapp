package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.mvibase.MviAction

sealed class MovieDetailAction: MviAction {

    data class LoadMovieDetailAction(val id:Int, val type:MovieListType):MovieDetailAction()
}