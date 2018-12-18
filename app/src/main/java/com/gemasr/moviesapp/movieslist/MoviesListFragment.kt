package com.gemasr.moviesapp.movieslist

import android.os.Bundle
import android.support.v4.app.Fragment
import com.gemasr.moviesapp.mvibase.MviView
import io.reactivex.Observable

class MoviesListFragment : Fragment(), MviView<MoviesListIntent, MoviesListViewState> {




    override fun intents(): Observable<MoviesListIntent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(state: MoviesListViewState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        private const val LIST_TYPE_ID = "TYPE_ID"

        operator fun invoke(taskId: String): MoviesListFragment {
            return MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putString(LIST_TYPE_ID, taskId)
                }
            }
        }
    }
}