package dev.syoritohatsuki.fstatsmobile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import dev.syoritohatsuki.fstatsmobile.data.api.FStatsApi
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import dev.syoritohatsuki.fstatsmobile.ui.components.ProjectItem
import org.koin.java.KoinJavaComponent.inject

@Composable
fun ProjectsScreen() {

    val api by inject<FStatsApi>(FStatsApi::class.java)

    val projects = remember { mutableStateListOf<Project>() }

    LaunchedEffect(key1 = "") {
        projects.clear()
        api.getProjects().apply {
            println("API: ${this.size}")
            projects.addAll(this)
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        projects.forEach {
            item {
                ProjectItem(it.name)
            }
        }
    }
}

