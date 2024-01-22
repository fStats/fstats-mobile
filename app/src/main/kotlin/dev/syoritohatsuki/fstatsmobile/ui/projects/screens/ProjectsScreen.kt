package dev.syoritohatsuki.fstatsmobile.ui.projects.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.syoritohatsuki.fstatsmobile.ui.projects.components.ProjectItem
import dev.syoritohatsuki.fstatsmobile.ui.projects.viewmodel.ProjectsViewModel

@Composable
fun ProjectsScreen() {

    val projectsViewModel: ProjectsViewModel = viewModel()

    val projects by projectsViewModel.movies.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        projects.forEach {
            item {
                ProjectItem(it.name, it.owner.username)
            }
        }
    }
}

