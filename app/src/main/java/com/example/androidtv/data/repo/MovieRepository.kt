package com.example.androidtv.data.repo

import com.example.androidtv.data.enums.ErrorType
import com.example.androidtv.data.model.Category
import com.example.androidtv.data.model.Movie
import com.example.androidtv.data.remote.ApiService
import com.example.androidtv.util.network.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun getMovies() = flow {
        emit(Resource.Loading())
        emit(Resource.Success(api.getMovies().categorized()))
    }.catch { e->
        emit(Resource.Error(e.message ,ErrorType.SERVER_ERROR))
    }

    private fun List<Movie>.categorized(): List<Category> {
        val genreSet = mutableSetOf<String>()
        for (movie in this) {
            for (genre in movie.genre) {
                genreSet.add(genre)
            }
        }
        val feedItems = mutableListOf<Category>()
        for ((index, genre) in genreSet.withIndex()) {
            val categoryId = index.toLong()
            val genreMovies = this.filter { it.genre.contains(genre) }
                .map { movie -> movie.copy().apply { this.categoryId = categoryId } }
                .sortedByDescending { it.year ?: 0 }
            feedItems.add(Category(categoryId, genre, genreMovies))
        }
        return feedItems
    }

}