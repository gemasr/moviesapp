package com.gemasr.moviesapp.movieslist

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gemasr.moviesapp.R
import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.mvibase.MviView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.find
import android.content.Intent
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gemasr.moviesapp.BuildConfig
import org.koin.android.viewmodel.ext.android.viewModel


class MoviesListFragment : Fragment(), MviView<MoviesListIntent, MoviesListViewState>, MoviesListAdapter.MoviesItemClickListener {

    val mViewModel by viewModel<MoviesListViewModel>()

    private val refreshIntentPublisher = PublishSubject.create<MoviesListIntent.LoadMoviesIntent>()

    private lateinit var mMoviestList: RecyclerView
    private lateinit var mAdapter : MoviesListAdapter
    private var mLoadingStarted: Boolean = false

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listType = arguments?.get(LIST_TYPE_ID) as Int
        mViewModel.type = when(listType){
            1 -> MovieListType.POPULAR
            2 -> MovieListType.TOP_RATED
            3 -> MovieListType.UPCOMING
            else -> MovieListType.POPULAR
        }

        mMoviestList = view.find(R.id.movies_list)
        val layoutManager = GridLayoutManager(context, 2)
        mMoviestList.layoutManager = layoutManager
        mAdapter = MoviesListAdapter(mutableListOf(), this)
        mMoviestList.adapter = mAdapter

        mMoviestList.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (!mLoadingStarted && (totalItemCount - visibleItemCount <= firstVisibleItem)) {
                    mLoadingStarted = true
                    mViewModel.page = mViewModel.page + 1
                    refreshIntentPublisher.onNext(MoviesListIntent.LoadMoviesIntent(mViewModel.page, mViewModel.type))
                }
            }
        })



    }

    override fun onStart() {
        super.onStart()

        bind()
    }


    override fun onResume() {
        super.onResume()

        if (mAdapter.isEmpty()){
            refreshIntentPublisher.onNext(MoviesListIntent.LoadMoviesIntent(mViewModel.page, mViewModel.type))
        }

    }


    private fun bind() {
        disposables.add(mViewModel.states().subscribe(this::render))
        mViewModel.processIntents(intents())
    }

    override fun intents(): Observable<MoviesListIntent> {
        return Observable.merge(
                initialIntent(),
                loadIntent()
        )
    }


    override fun render(state: MoviesListViewState) {
        if (state.addedMovies.isNotEmpty()){
            mAdapter.addNewMovies(state.addedMovies)

            mLoadingStarted = false
        }
    }

    private fun initialIntent(): Observable<MoviesListIntent.InitialIntent> {
        return Observable.just(MoviesListIntent.InitialIntent)
    }

    private fun loadIntent(): Observable<MoviesListIntent.LoadMoviesIntent> {
        return refreshIntentPublisher
    }


    override fun onListItemClick(int: Int, item: Movie, image: AppCompatImageView) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ITEM_ID, item.id)
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_TYPE, mViewModel.type.value)
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ITEM_IMAGE, BuildConfig.MOV_API_IMAGES_URL+item.posterpath)
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(image) ?: item.id.toString())

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity as Activity,
                image,
                ViewCompat.getTransitionName(image) ?: item.id.toString())

        startActivity(intent, options.toBundle())
    }

    companion object {
        private const val LIST_TYPE_ID = "TYPE_ID"

        operator fun invoke(type: MovieListType): MoviesListFragment {
            return MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putInt(LIST_TYPE_ID, type.value)
                }
            }
        }
    }
}