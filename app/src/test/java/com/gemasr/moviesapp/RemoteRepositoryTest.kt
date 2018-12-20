package com.gemasr.moviesapp

import com.gemasr.moviesapp.api.MoviesApiService
import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.remote.RemoteMoviesDataSource
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.concurrent.TimeUnit

class RepositoryTest {


    private var server = MockWebServer()
    private val apiService = MoviesApiService("http://localhost:8000")
    private val remoteDataSource = RemoteMoviesDataSource(apiService)

    @Before
    fun setUp() {
        server.start(8000)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun receivedEmptyListOfMovies_emptyList() {
        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody("{results:[]}")
        server.enqueue(mockResponse)

        val observer = TestObserver<List<Movie>>()
        remoteDataSource.getPopularMovies(1).subscribe(observer)
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

        observer.assertNoErrors()
        observer.assertValueCount(1)
        assertEquals(1, observer.values().size)
        assertEquals(0, observer.values()[0].size)
    }


    @Test
    fun receivedListOfMovies() {
    val mockResponse = MockResponse()
    .setResponseCode(200)
    .setBody(UnitTestHelper.getJson(path = "json/movies.json"))
    server.enqueue(mockResponse)

    val observer = TestObserver<List<Movie>>()
        remoteDataSource.getPopularMovies(1).subscribe(observer)
    observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

    observer.assertNoErrors()
    observer.assertValueCount(1)
    assertEquals(1,observer.values().size)
    assertEquals(20,observer.values()[0].size)
    }



    @Test
    fun correctMovieParsing() {
        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(UnitTestHelper.getJson(path = "json/movies.json"))
        server.enqueue(mockResponse)

        val observer = TestObserver<List<Movie>>()
        remoteDataSource.getPopularMovies(1).subscribe(observer)
        observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

        observer.assertNoErrors()
        observer.assertValueCount(1)
        assertEquals(1,observer.values().size)
        assertEquals(20,observer.values()[0].size)
        val movie = observer.values()[0][0]
        assertEquals(297802, movie.id)
        assertEquals("Aquaman",movie.title)
        assertEquals("2018-12-07", movie.releaseDate)
        assertEquals("Mock overview", movie.overview)
        assertEquals("/ydUpl3QkVUCHCq1VWvo2rW4Sf7y.jpg", movie.posterpath)
    }



    @Test
    fun receivedErrorTest () {
    val mockResponse = MockResponse()
    .setResponseCode(500)

    server.enqueue(mockResponse)

    val observer = TestObserver<List<Movie>>()
        remoteDataSource.getPopularMovies(1).subscribe(observer)
    observer.awaitTerminalEvent(2, TimeUnit.SECONDS)

    observer.assertErrorMessage("HTTP 500 Server Error")
    observer.assertValueCount(0)
    }


}