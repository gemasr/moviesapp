package com.gemasr.moviesapp.movieslist

import android.support.v4.app.Fragment
import com.gemasr.moviesapp.mvibase.MviView
import io.reactivex.Observable

class MovieDetailFragment : Fragment(), MviView<MovieDetailIntent, MovieDetailViewState> {

    override fun intents(): Observable<MovieDetailIntent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(state: MovieDetailViewState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}