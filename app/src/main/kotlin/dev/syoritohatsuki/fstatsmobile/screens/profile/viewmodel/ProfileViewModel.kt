package dev.syoritohatsuki.fstatsmobile.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.syoritohatsuki.fstatsmobile.data.api.FStatsApi
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class ProfileViewModel : ViewModel() {
    private val api by KoinJavaComponent.inject<FStatsApi>(FStatsApi::class.java)

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects

    fun getUserProjects(userId: Int) {
        viewModelScope.launch {
            _projects.emit(api.getProjectByUserId(userId))
        }
    }
}