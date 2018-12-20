package com.gemasr.moviesapp.movieslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gemasr.moviesapp.R
import com.gemasr.moviesapp.data.source.local.MovieListType
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_movies_list.*

class MoviesListActivity : AppCompatActivity() {

    var listFragment = MoviesListFragment(MovieListType.POPULAR)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_popular -> {
                showFragmentOfType(MovieListType.POPULAR)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_top_rated -> {
                showFragmentOfType(MovieListType.TOP_RATED)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_upcoming -> {
                showFragmentOfType(MovieListType.UPCOMING)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        loadFragment(listFragment)
    }


    private fun loadFragment(fragment: Fragment): Boolean {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.movies_list_container, fragment)
                    .commit()
            return true
        }
        return false
    }


    fun showFragmentOfType(type:MovieListType){
        supportFragmentManager.beginTransaction().detach(listFragment).commit()
        listFragment = MoviesListFragment(type)
        loadFragment(listFragment)
    }
}
