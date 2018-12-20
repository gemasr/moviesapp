package com.gemasr.moviesapp.movieslist

import androidx.lifecycle.ViewModel
import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.ext.notOfType
import com.gemasr.moviesapp.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

class MoviesListViewModel(
        private val actionProcessorHolder: MoviesListProcessorHolder
): ViewModel(), MviViewModel<MoviesListIntent, MoviesListViewState> {

    var type: MovieListType = MovieListType.POPULAR
    var page = 1

    private val intentsSubject: PublishSubject<MoviesListIntent> = PublishSubject.create()
    private val statesObservable: Observable<MoviesListViewState> = compose()


    override fun processIntents(intents: Observable<MoviesListIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<MoviesListViewState> = statesObservable


    private fun compose(): Observable<MoviesListViewState> {
        return intentsSubject
                .compose<MoviesListIntent>(intentFilter)
                .map(this::actionFromIntent)
                .compose(actionProcessorHolder.actionProcessor)
                .scan(MoviesListViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }


    private val intentFilter: ObservableTransformer<MoviesListIntent, MoviesListIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge<MoviesListIntent>(
                        shared.ofType(MoviesListIntent.InitialIntent::class.java).take(1),
                        shared.notOfType(MoviesListIntent.InitialIntent::class.java)
                )
            }
        }


    private fun actionFromIntent(intent: MoviesListIntent): MoviesListAction {
        return when (intent) {
            is MoviesListIntent.LoadMoviesIntent -> MoviesListAction.LoadMoviesAction(intent.page, intent.type)
            is MoviesListIntent.InitialIntent -> MoviesListAction.LoadMoviesAction(1, type)
        }
    }



    companion object {
        private val reducer = BiFunction { previousState: MoviesListViewState, result: MoviesListResult ->
            when (result) {
                is MoviesListResult.LoadMoviesResult -> when (result) {
                    is MoviesListResult.LoadMoviesResult.Success -> {
                        previousState.copy(
                                isLoading = false,
                                movies = previousState.movies.union(result.movies).toList(),
                                addedMovies = result.movies,
                                page = result.page,
                                moviesType = result.type
                        )
                    }
                    is MoviesListResult.LoadMoviesResult.Failure -> previousState.copy(isLoading = false, addedMovies = listOf(), error = result.error)
                    is MoviesListResult.LoadMoviesResult.InFlight -> previousState.copy(isLoading = true, addedMovies = listOf())
                }

            }
        }
    }



}