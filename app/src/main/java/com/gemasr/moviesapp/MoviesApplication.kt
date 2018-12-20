package com.gemasr.moviesapp

import android.app.Application
import com.gemasr.moviesapp.di.moviesAppModule
import org.koin.android.ext.android.startKoin
import androidx.appcompat.app.AppCompatDelegate


class MoviesApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(moviesAppModule))

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES)

    }

}