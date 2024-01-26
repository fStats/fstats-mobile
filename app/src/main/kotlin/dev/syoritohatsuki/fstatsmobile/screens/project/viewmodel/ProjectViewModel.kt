package dev.syoritohatsuki.fstatsmobile.screens.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.syoritohatsuki.fstatsmobile.data.api.FStatsApi
import dev.syoritohatsuki.fstatsmobile.data.dto.ProjectLine
import dev.syoritohatsuki.fstatsmobile.data.dto.ProjectPie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class ProjectViewModel(private val projectId: Int) : ViewModel() {

    private val api by KoinJavaComponent.inject<FStatsApi>(FStatsApi::class.java)

    private val _projectPie = MutableStateFlow<ProjectPie?>(null)
    val projectPie: StateFlow<ProjectPie?> = _projectPie

    private val _projectLine = MutableStateFlow<ProjectLine?>(null)
    val projectLine: StateFlow<ProjectLine?> = _projectLine

    init {
        fetchProjectPie()
        fetchProjectLine()
    }

    private fun fetchProjectPie() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedProjectPie = api.getMetricsCount(projectId)
            _projectPie.emit(fetchedProjectPie)
        }
    }

    private fun fetchProjectLine() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedProjectLine = api.getMetricsLine(projectId)
            _projectLine.emit(fetchedProjectLine)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ProjectViewModelFactory(private val projectId: Int) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ProjectViewModel(projectId) as T
}