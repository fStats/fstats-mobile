package dev.syoritohatsuki.fstatsmobile.screens.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.syoritohatsuki.fstatsmobile.data.api.FStatsApi
import dev.syoritohatsuki.fstatsmobile.data.dto.MetricLine
import dev.syoritohatsuki.fstatsmobile.data.dto.MetricPie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class ProjectViewModel(private val projectId: Int) : ViewModel() {

    private val api by KoinJavaComponent.inject<FStatsApi>(FStatsApi::class.java)

    private val _metricPie = MutableStateFlow(emptyMap<String, Map<String?, Int>>())
    val metricPie: StateFlow<MetricPie> = _metricPie

    private val _metricLine = MutableStateFlow(MetricLine())
    val metricLine: StateFlow<MetricLine> = _metricLine

    init {
        fetchProjectPie()
        fetchProjectLine()
    }

    private fun fetchProjectPie() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedProjectPie = api.getMetricsCount(projectId)
            _metricPie.emit(fetchedProjectPie)
        }
    }

    private fun fetchProjectLine() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedProjectLine = api.getMetricsLine(projectId)
            _metricLine.emit(fetchedProjectLine)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ProjectViewModelFactory(private val projectId: Int) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ProjectViewModel(projectId) as T
}