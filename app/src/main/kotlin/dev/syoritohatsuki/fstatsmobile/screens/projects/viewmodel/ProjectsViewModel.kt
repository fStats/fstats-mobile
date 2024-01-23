package dev.syoritohatsuki.fstatsmobile.screens.projects.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.syoritohatsuki.fstatsmobile.data.api.FStatsApi
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class ProjectsViewModel : ViewModel() {

    private val api by KoinJavaComponent.inject<FStatsApi>(FStatsApi::class.java)

    private val _movies = MutableStateFlow<List<Project>>(emptyList())
    val movies: StateFlow<List<Project>> = _movies

    init {
        fetchProjects()
    }

    private fun fetchProjects() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedMovies = api.getProjects()
            _movies.emit(fetchedMovies)
        }
    }
}