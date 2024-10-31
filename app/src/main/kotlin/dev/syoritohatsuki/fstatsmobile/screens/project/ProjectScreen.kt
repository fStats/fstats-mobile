package dev.syoritohatsuki.fstatsmobile.screens.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.syoritohatsuki.fstatsmobile.Screens
import dev.syoritohatsuki.fstatsmobile.screens.project.components.LineChart
import dev.syoritohatsuki.fstatsmobile.screens.project.components.PagerNavigator
import dev.syoritohatsuki.fstatsmobile.screens.project.components.PieChart
import dev.syoritohatsuki.fstatsmobile.screens.project.viewmodel.ProjectViewModel
import dev.syoritohatsuki.fstatsmobile.screens.project.viewmodel.ProjectViewModelFactory

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable

fun ProjectScreen(navController: NavController, navBackStackEntry: NavBackStackEntry) {

    val pagerState = rememberPagerState(pageCount = { 7 })

    val type = remember { mutableStateOf("") }

    val projectId: Int? = navBackStackEntry.arguments?.getInt(Screens.Project.argument)

    if (projectId == null) {
        navController.navigateUp()
        return
    }

    val projectViewModel: ProjectViewModel = viewModel(factory = ProjectViewModelFactory(projectId))

    val metricPie by projectViewModel.metricPie.collectAsState()
    val metricLine by projectViewModel.metricLine.collectAsState()

    Scaffold(topBar = {
        if (metricLine.timestamps.isNotEmpty()) LineChart(metricLine) else Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Project don't have data yet for display")
        }
    }, content = {
        if (metricPie.isNotEmpty()) {
            HorizontalPager(state = pagerState) { page ->
                type.value = metricPie.keys.elementAtOrElse(page) { "" }
                metricPie[type.value]?.let { values ->
                    PieChart(values, it)
                }
            }
        } else Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Project don't have data for last 30 minute to display")
        }
    }, bottomBar = {
        if (metricPie.isNotEmpty()) PagerNavigator(type, pagerState)
    }, modifier = Modifier.fillMaxSize()
    )
}