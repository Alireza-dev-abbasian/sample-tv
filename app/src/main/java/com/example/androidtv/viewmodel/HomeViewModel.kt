package com.example.androidtv.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.androidtv.data.enums.ErrorType
import com.example.androidtv.data.model.Category
import com.example.androidtv.data.repo.MovieRepository
import com.example.androidtv.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app:Application,
    private val movieRepo: MovieRepository
) : BaseViewModel(app) {

    private val _moviesResponse = MutableStateFlow<Resource<List<Category>>>(Resource.Loading())
    val moviesResponse = _moviesResponse.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() = viewModelScope.launch {
        with(_moviesResponse) {
            tryEmit(Resource.Loading())
            movieRepo.getMovies()
                .catch { e->
                    tryEmit(Resource.Error(e.message , ErrorType.SERVER_ERROR))
                }
                .collect {
                    tryEmit(Resource.Success(it.data))
                }
        }
    }

}