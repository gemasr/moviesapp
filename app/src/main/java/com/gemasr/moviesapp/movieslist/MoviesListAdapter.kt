package com.gemasr.moviesapp.movieslist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.gemasr.moviesapp.BuildConfig
import com.gemasr.moviesapp.R
import com.gemasr.moviesapp.data.model.Movie
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class MoviesListAdapter(val data:MutableList<Movie>, val itemClickListener: MoviesItemClickListener) : RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {

    interface MoviesItemClickListener{
        fun onListItemClick(int:Int, item:Movie, image: AppCompatImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MoviesListViewHolder = MoviesListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_movie, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(viewHolder: MoviesListViewHolder, position: Int) {
        val movie = data[position]

        Picasso.get().load(BuildConfig.MOV_API_IMAGES_URL+movie.posterpath).into(viewHolder.mImageView)

        viewHolder.mTitle.text = movie.title

        ViewCompat.setTransitionName(viewHolder.mImageView, movie.id.toString())

        viewHolder.itemView.setOnClickListener{
            itemClickListener?.onListItemClick(viewHolder.adapterPosition, movie, viewHolder.mImageView)

        }


    }

    fun isEmpty(): Boolean {
        return data.size == 0
    }

    fun addNewMovies(addedMovies: List<Movie>) {
        addedMovies.forEach {movie->
            if (data.filter { it.id == movie.id }.isEmpty()){
                data.add(movie)
            }
        }
        notifyDataSetChanged()
    }


    inner class MoviesListViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){

        internal val mImageView: AppCompatImageView = itemView.find(R.id.item_movie_poster_image)
        internal val mTitle: TextView = itemView.find(R.id.item_movie_title)

    }
}